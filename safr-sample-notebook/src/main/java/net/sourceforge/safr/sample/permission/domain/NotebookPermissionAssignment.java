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
package net.sourceforge.safr.sample.permission.domain;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.notebook.domain.Notebook;

/**
 * Defines a user's (permission assignee's) access to a notebook instance.
 * 
 * @author Martin Krasser
 */
public class NotebookPermissionAssignment {

    private String assigneeId;
    
    private String ownerId;
    
    private String notebookId;
    
    private Action action;

    /**
     * Creates a new NotebookPermission instance.
     * 
     * @param assigneeId assignee identifier of the permission assignment.
     * @param ownerId owner identifier (target context) of the permission assignment.
     * @param notebookId notebook identifier (target identifier) of the permission assignment.
     * @param action access action or <code>null</code> if access shall be
     *        denied.
     */
    public NotebookPermissionAssignment(String assigneeId, String ownerId, String notebookId, Action action) {
        this.assigneeId = assigneeId;
        this.ownerId = ownerId;
        this.notebookId = notebookId;
        this.action = action;
    }

    /**
     * Creates a new NotebookPermission instance.
     * 
     * @param assigneeId assignee identifier of the permission assignment.
     * @param instancePermission notebook instance permission.
     */
    public NotebookPermissionAssignment(String assigneeId, InstancePermission instancePermission) {
        String classifier = instancePermission.getTarget().getClassifier();
        if (!classifier.equals(Notebook.class.getName())) {
            throw new IllegalArgumentException(
                    "invalid instance permission classifier: " + classifier + 
                    ", must be: " + Notebook.class.getName());
        }
        this.assigneeId = assigneeId;
        this.ownerId = instancePermission.getTarget().getContext();
        this.notebookId = instancePermission.getTarget().getIdentifier();
        this.action = instancePermission.getAction();
        
    }
    
    public Action getAction() {
        return action;
    }

    public String getOwnerId() {
        return ownerId;
    }
    
    public String getNotebookId() {
        return notebookId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    /**
     * Creates a {@link UserPrincipal} from the <code>assigneeId</code> of
     * this assignment.
     * 
     * @return a {@link UserPrincipal} instance.
     */
    public UserPrincipal createUserPrincipal() {
        return new UserPrincipal(assigneeId);
    }
    
    /**
     * Creates a new {@link InstancePermission} out of this permission assignment.
     * 
     * @return a new {@link InstancePermission} object or null if this
     *         permission's <code>action</code> property is <code>null</code>.
     */
    public InstancePermission createInstancePermission() {
        if (action == null) {
            return null;
        }
        Target t = new Target(ownerId, Notebook.class.getName(), notebookId);
        return new InstancePermission(t, action);
    }
    
}
