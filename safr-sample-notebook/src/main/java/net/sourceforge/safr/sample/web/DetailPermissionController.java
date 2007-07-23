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
package net.sourceforge.safr.sample.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;
import net.sourceforge.safr.sample.permission.domain.PermissionAssignment;
import net.sourceforge.safr.sample.permission.service.PermissionService;
import net.sourceforge.safr.sample.usermgnt.service.UserService;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @author Martin Krasser
 */
public class DetailPermissionController extends SimpleFormController {

    private NotebookService notebookService;
    
    private PermissionControllerHelper helper;
    
    public DetailPermissionController() {
        super();
        helper = new PermissionControllerHelper();
    }
    
    public PermissionService getPermissionService() {
        return helper.getPermissionService();
    }

    public void setPermissionService(PermissionService permissionService) {
        helper.setPermissionService(permissionService);
    }

    public UserService getUserService() {
        return helper.getUserService();
    }

    public void setUserService(UserService userService) {
        helper.setUserService(userService);
    }

    public NotebookService getNotebookService() {
        return notebookService;
    }

    public void setNotebookService(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @Override
    protected Map<String, Notebook> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Notebook notebook = getNotebookService().findNotebook(request.getParameter("notebookId"));
        Map<String, Notebook> map = new HashMap<String, Notebook>(1);
        map.put("notebook", notebook);
        return map;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String assigneeId = request.getParameter("assigneeId");
        String notebookId = request.getParameter("notebookId");
        Notebook notebook = notebookService.findNotebook(notebookId);
        PermissionAssignment assignment = helper.getAssignment(notebook, assigneeId);
        return assignment;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        getPermissionService().applyPermissionAssignments((PermissionAssignment)command);
        Notebook notebook = getNotebookService().findNotebook(request.getParameter("notebookId"));
        Collection<PermissionAssignment> assignments = helper.getAssignments(notebook);
        Map map = new HashMap();
        map.put("notebook", notebook);
        map.put("assignments", assignments);
        map.putAll(errors.getModel());
        return new ModelAndView(getSuccessView(), map);
    }

}
