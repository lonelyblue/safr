package net.sourceforge.safr.jaas.policy;

import java.security.Principal;

import net.sourceforge.safr.jaas.permission.InstancePermission;

public interface PermissionManagementLog {

    void addGrantTask(Principal principal, InstancePermission permission);
    
    void addRevokeTask(Principal principal, InstancePermission permission);
    
    void commit();
    
}
