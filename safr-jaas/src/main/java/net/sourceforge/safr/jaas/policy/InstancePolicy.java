/*
 * Copyright 2006-2007 InterComponentWare AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.safr.jaas.policy;

import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.Principal;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.RolePrincipal;
import net.sourceforge.safr.jaas.principal.UserPrincipal;

/**
 * @author Martin Krasser
 */
public class InstancePolicy extends Policy implements PermissionManager {
    
    private PermissionMap userPermissions;
    private PermissionMap rolePermissions;

    private Policy defaultPolicy;
    
    public InstancePolicy() {
        this.userPermissions = new PermissionMap();
        this.rolePermissions = new PermissionMap();
        bootstrap();
    }
    
    public void setDefaultPolicy(Policy defaultPolicy) {
        this.defaultPolicy = defaultPolicy;
    }
    
    @Override
    public boolean implies(ProtectionDomain domain, Permission permission) {
        if (!(permission instanceof InstancePermission)) {
            return defaultPolicy.implies(domain, permission);
        }
        if (userPermissionsImply(permission, getUserId(domain))) {
            return true;
        }
        return rolePermissionsImply(permission, getRoleIds(domain));
    }

    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public void refresh() {
        throw new UnsupportedOperationException("not implemented");
    }

    public Collection<InstancePermission> getPermissions(Principal principal) {
        return Collections.unmodifiableList(getPermissionMap(principal).getEager(principal.getName()));
    }
    
    public PermissionManagementLog newPermissionManagementLog() {
        return new PermissionManagementLogImpl(this);
    }
    
    public void grantPermission(Principal principal, InstancePermission permission) {
        checkPermission(permission);
        grantPermissionNoCheck(principal, permission);
    }
    
    public void revokePermission(Principal principal, InstancePermission permission) {
        checkPermission(permission);
        revokePermissionNoCheck(principal, permission);
    }
    
    private void grantPermissionNoCheck(Principal principal, InstancePermission permission) {
        getPermissionMap(principal).getEager(principal.getName()).add(permission);
    }
    
    private void revokePermissionNoCheck(Principal principal, InstancePermission permission) {
        getPermissionMap(principal).getEager(principal.getName()).remove(permission);
    }
    
    private PermissionMap getPermissionMap(Principal principal) {
        if (principal instanceof UserPrincipal) {
            return userPermissions;
        }
        if (principal instanceof RolePrincipal) {
            return rolePermissions;
        }
        throw new IllegalArgumentException("unsupported principal class " + principal.getClass());
    }
    
    private boolean userPermissionsImply(Permission permission, String userId) {
        if (userId == null) {
            return false;
        }
        return userPermissions.getEager(userId).implies(permission);
        
    }
    
    private boolean rolePermissionsImply(Permission permission, List<String> roleIds) {
        for (String roleId : roleIds) {
            if (rolePermissions.getEager(roleId).implies(permission)) {
                return true;
            }
        }
        return false;
    }
    
    private void bootstrap() {
        userPermissions.getEager("root").add(new InstancePermission(new Target(), Action.AUTH));
    }
    
    private static void checkPermission(InstancePermission permission) {
        AccessController.checkPermission(permission.createAuthorizationPermission());
    }
    
    private static String getUserId(ProtectionDomain domain) {
        for (Principal p : domain.getPrincipals()) {
            if (p instanceof UserPrincipal) {
                return p.getName();
            }
        }
        return null;
    }
    
    private static List<String> getRoleIds(ProtectionDomain domain) {
        ArrayList<String> result = new ArrayList<String>(); 
        for (Principal p : domain.getPrincipals()) {
            if (p instanceof RolePrincipal) {
                result.add(p.getName());
            }
        }
        return result;
    }
    
    @SuppressWarnings("serial")
    private static class PermissionMap extends HashMap<String, PermissionList> {

        @Override
        public synchronized void clear() {
            super.clear();
        }

        public synchronized PermissionList getEager(String userId) {
            PermissionList result = get(userId);
            if (result == null) {
                result = new PermissionList();
                put(userId, result);
            }
            return result;
        }
        
    }
    
    @SuppressWarnings("serial")
    private static class PermissionList extends ArrayList<InstancePermission> {

        @Override
        public synchronized boolean add(InstancePermission o) {
            return super.add(o);
        }

        @Override
        public synchronized boolean remove(Object o) {
            return super.remove(o);
        }

        public synchronized boolean implies(Permission permission) {
            for (Permission p : this) {
                if (p.implies(permission)) {
                    return true;
                }
            }
            return false;
        }

    }

    private static class PermissionManagementLogImpl implements PermissionManagementLog {

        private InstancePolicy manager;

        private Set<LogEntry> entries;
        
        public PermissionManagementLogImpl(InstancePolicy manager) {
            this.manager = manager;
            this.entries = new HashSet<LogEntry>();
        }

        public void addGrantTask(Principal principal, InstancePermission permission) {
            entries.add(new LogEntry(true, principal, permission));
        }

        public void addRevokeTask(Principal principal, InstancePermission permission) {
            entries.add(new LogEntry(false, principal, permission));
        }

        public void commit() {
            for (LogEntry entry : entries) {
                checkPermission(entry.getPermission());
            }
            for (LogEntry entry : entries) {
                if (entry.isGrant()) {
                    manager.grantPermissionNoCheck(entry.getPrincipal(), entry.getPermission());
                } else {
                    manager.revokePermissionNoCheck(entry.getPrincipal(), entry.getPermission());
                }
            }
        }
        
    }
    
    private static class LogEntry {
        
        private boolean grant;
        
        private Principal principal;
        
        private InstancePermission permission;

        public LogEntry(boolean grant, Principal principal, InstancePermission permission) {
            this.grant = grant;
            this.principal = principal;
            this.permission = permission;
        }

        public boolean isGrant() {
            return grant;
        }

        public Principal getPrincipal() {
            return principal;
        }
     
        public InstancePermission getPermission() {
            return permission;
        }
        
    }

}
