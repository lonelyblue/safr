package net.sourceforge.safr.sample.permission.domain;

import net.sourceforge.safr.jaas.permission.Action;
import net.sourceforge.safr.jaas.permission.InstancePermission;
import net.sourceforge.safr.jaas.permission.Target;
import net.sourceforge.safr.jaas.principal.UserPrincipal;
import net.sourceforge.safr.sample.notebook.domain.Notebook;

/**
 * Defines a user's access to a notebook instance.
 * 
 * @author Martin Krasser
 */
public class NotebookPermissionAssignment {

    private UserPrincipal userPrincipal;
    
    private String notebookOwnerId;
    
    private String notebookId;
    
    private Action action;

    /**
     * Creates a new NotebookPermission instance.
     * 
     * @param userId assignee of the permission.
     * @param notebookOwnerId user id of notebook owner (permission context).
     * @param notebookId id of notebook (permission identifier)
     * @param action access action or <code>null</code> if access shall be
     *        denied.
     */
    public NotebookPermissionAssignment(String userId, String notebookOwnerId, String notebookId, Action action) {
        this.userPrincipal = new UserPrincipal(userId);
        this.notebookOwnerId = notebookOwnerId;
        this.notebookId = notebookId;
        this.action = action;
    }

    /**
     * Creates a new NotebookPermission instance.
     * 
     * @param userPrincipal assignee user principal.
     * @param instancePermission notebook instance permission.
     */
    public NotebookPermissionAssignment(UserPrincipal userPrincipal, InstancePermission instancePermission) {
        String classifier = instancePermission.getTarget().getClassifier();
        if (!classifier.equals(Notebook.class.getName())) {
            throw new IllegalArgumentException(
                    "invalid instance permission classifier: " + classifier + 
                    ", must be: " + Notebook.class.getName());
        }
        this.userPrincipal = userPrincipal;
        this.notebookOwnerId = instancePermission.getTarget().getContext();
        this.notebookId = instancePermission.getTarget().getIdentifier();
        this.action = instancePermission.getAction();
        
    }
    
    public Action getAction() {
        return action;
    }

    public String getNotebookOwnerId() {
        return notebookOwnerId;
    }
    
    public String getNotebookId() {
        return notebookId;
    }

    public String getUserId() {
        return userPrincipal.getName();
    }

    /**
     * Returns the assigne {@link UserPrincipal} of this assignment. 
     * 
     * @return a {@link UserPrincipal} instance.
     */
    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
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
        Target t = new Target(notebookOwnerId, Notebook.class.getName(), notebookId);
        return new InstancePermission(t, action);
    }
    
}
