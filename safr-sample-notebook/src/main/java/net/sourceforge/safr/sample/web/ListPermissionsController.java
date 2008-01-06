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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author Martin Krasser
 */
public class ListPermissionsController extends AbstractController {

    @Autowired
    private NotebookService notebookService;
    
    @Autowired
    private PermissionControllerHelper helper;
    
    public PermissionService getPermissionService() {
        return helper.getPermissionService();
    }

    public UserService getUserService() {
        return helper.getUserService();
    }

    public NotebookService getNotebookService() {
        return notebookService;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
        Notebook notebook = getNotebookService().findNotebook(request.getParameter("notebookId"));
        Collection<PermissionAssignment> assignments = helper.getAssignments(notebook);
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("assignments", assignments);
        map.put("notebook", notebook);
        return new ModelAndView("permissionList", map);
    }

}
