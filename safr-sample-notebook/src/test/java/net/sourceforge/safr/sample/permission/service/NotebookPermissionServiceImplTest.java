package net.sourceforge.safr.sample.permission.service;

import static org.junit.Assert.*;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.policy.InstancePolicy;
import net.sourceforge.safr.sample.permission.domain.NotebookPermissionAssignment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NotebookPermissionServiceImplTest {

    private NotebookPermissionServiceImpl notebookPermissionService;
    
    private InstancePolicy permissionManager;
    
    @Before
    public void setUp() throws Exception {
        notebookPermissionService = new NotebookPermissionServiceImpl();
        permissionManager = new InstancePolicy();
        notebookPermissionService.setPermissionManager(permissionManager);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testApplyNotebookPermissionAssignments() {
        NotebookPermissionAssignment assignment = null;

        // create new assignment
        assignment = new NotebookPermissionAssignment("u1", "o1", "1", Action.WRITE);
        notebookPermissionService.applyNotebookPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.getUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.getUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // upgrade assignment 
        assignment = new NotebookPermissionAssignment("u1", "o1", "1", Action.MANAGE);
        notebookPermissionService.applyNotebookPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.getUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.getUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // same assignment
        assignment = new NotebookPermissionAssignment("u1", "o1", "1", Action.MANAGE);
        notebookPermissionService.applyNotebookPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.getUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.getUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // cancel assignment
        assignment = new NotebookPermissionAssignment("u1", "o1", "1", null);
        notebookPermissionService.applyNotebookPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 0, 
                permissionManager.getPermissions(assignment.getUserPrincipal()).size());
        
    }
    
}
