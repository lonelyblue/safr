package net.sourceforge.safr.sample.permission.service;

import java.util.Collection;

import net.sourceforge.safr.jaas.policy.PermissionManager;
import net.sourceforge.safr.sample.permission.domain.NotebookPermissionAssignment;

/**
 * A {@link PermissionManager} facade for managing NotebookPermissions. 
 * 
 * @author Martin Krasser
 */
public interface NotebookPermissionService {

    Collection<NotebookPermissionAssignment> getNotebookPermissionAssignments(String userId);
    
    void applyNotebookPermissionAssignments(NotebookPermissionAssignment... notebookPermissions);
    
}
