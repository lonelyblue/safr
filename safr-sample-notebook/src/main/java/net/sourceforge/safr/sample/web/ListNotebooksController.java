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
package net.sourceforge.safr.sample.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;
import net.sourceforge.safr.sample.usermgnt.domain.User;
import net.sourceforge.safr.sample.usermgnt.service.UserService;
import net.sourceforge.safr.sample.web.support.NotebookIdContainer;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @author Martin Krasser
 */
public class ListNotebooksController extends SimpleFormController {

	private NotebookService notebookService;

	private UserService userService;
	
    public NotebookService getNotebookService() {
        return notebookService;
    }

    public void setNotebookService(NotebookService service) {
        this.notebookService = service;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(1);
        map.put("notebooks", getNotebookService().findNotebooks());
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        NotebookIdContainer container = ((NotebookIdContainer)command);
        User current = getUserService().currentUser();
        Notebook notebook = new Notebook(container.getIdentifier(), current);
        getNotebookService().createNotebook(notebook);
        Map map = new HashMap();
        map.putAll(errors.getModel());
        map.put("notebooks", getNotebookService().findNotebooks());
        return new ModelAndView(getSuccessView(), map);
    }

}
