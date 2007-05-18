/*
 * Copyright 2007 the original author or authors.
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
package net.sourceforge.safr.sample.permission.service;

import java.util.ArrayList;
import java.util.Collection;

import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.policy.PermissionManagementLog;
import net.sourceforge.safr.jaas.policy.PermissionManager;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.permission.domain.NotebookPermissionAssignment;

/**
 * @author Martin Krasser
 */
public class NotebookPermissionServiceImpl implements NotebookPermissionService {

    private PermissionManager permissionManager;
    
    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }
    
    public Collection<NotebookPermissionAssignment> getNotebookPermissionAssignments(String userId) {
        ArrayList<NotebookPermissionAssignment> result = new ArrayList<NotebookPermissionAssignment>();
        UserPrincipal up = new UserPrincipal(userId);
        for (InstancePermission ip : permissionManager.getPermissions(up)) {
            result.add(new NotebookPermissionAssignment(up.getName(), ip));
        }
        return null;
    }
    
    public void applyNotebookPermissionAssignments(NotebookPermissionAssignment... npas) {
        PermissionManagementLog log = permissionManager.newPermissionManagementLog();
        for (NotebookPermissionAssignment npa : npas) {
            applyNotebookPermissionAssignment(npa, log);
        }
        log.commit();
    }

    private void applyNotebookPermissionAssignment(NotebookPermissionAssignment npa, PermissionManagementLog log) {
        // Permission already granted to user
        InstancePermission ipCurrent = getInstancePermission(npa);
        // Permission to be granted to user
        InstancePermission ipDefined = npa.createInstancePermission();
        if (ipCurrent == null) {
            if (ipDefined != null) {
                // grant new permission
                log.addGrantTask(npa.createUserPrincipal(), ipDefined);
            }
        } else {
            if (ipDefined == null) {
                // revoke existing permission
                log.addRevokeTask(npa.createUserPrincipal(), ipCurrent);
            } else if (ipCurrent.equals(ipDefined)) {
                // nothing to do
            } else {
                // revoke existing permission
                log.addRevokeTask(npa.createUserPrincipal(), ipCurrent);
                // and grant new permission
                log.addGrantTask(npa.createUserPrincipal(), ipDefined);
            }
        }
    }
    
    private InstancePermission getInstancePermission(NotebookPermissionAssignment npa) {
        Collection<InstancePermission> ips = null; 
        String userId = npa.getAssigneeId();
        String ntbkId = npa.getNotebookId();
        ips = permissionManager.getPermissions(new UserPrincipal(userId));
        if (ips == null) {
            return null;
        }
        for (InstancePermission ip : ips) {
            if (!ip.getTarget().getClassifier().equals(Notebook.class.getName())) {
                continue;
            }
            if (ip.getTarget().getIdentifier().equals(ntbkId)) {
                return ip; 
            }
        }
        return null;
    }

}
