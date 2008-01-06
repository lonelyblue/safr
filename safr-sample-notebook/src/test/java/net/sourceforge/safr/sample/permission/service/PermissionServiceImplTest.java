/*
 * Copyright 2007-2008 the original author or authors.
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

import static org.junit.Assert.*;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.policy.InstancePolicy;
import net.sourceforge.safr.sample.permission.domain.PermissionAssignment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PermissionServiceImplTest {

    private PermissionServiceImpl notebookPermissionService;
    
    private InstancePolicy permissionManager;
    
    @Before
    public void setUp() throws Exception {
        notebookPermissionService = new PermissionServiceImpl();
        permissionManager = new InstancePolicy();
        notebookPermissionService.setPermissionManager(permissionManager);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testApplyPermissionAssignments() {
        PermissionAssignment assignment = null;

        // create new assignment
        assignment = new PermissionAssignment("u1", "o1", "1", Action.WRITE);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.createUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // upgrade assignment 
        assignment = new PermissionAssignment("u1", "o1", "1", Action.MANAGE);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.createUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // same assignment
        assignment = new PermissionAssignment("u1", "o1", "1", Action.MANAGE);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 1, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        assertTrue("wrong permissions content", 
                permissionManager.getPermissions(assignment.createUserPrincipal()).contains(assignment.createInstancePermission()));
        
        // cancel assignment
        assignment = new PermissionAssignment("u1", "o1", "1", null);
        notebookPermissionService.applyPermissionAssignments(assignment);
        assertEquals("wrong number of permissions", 0, 
                permissionManager.getPermissions(assignment.createUserPrincipal()).size());
        
    }
    
}
