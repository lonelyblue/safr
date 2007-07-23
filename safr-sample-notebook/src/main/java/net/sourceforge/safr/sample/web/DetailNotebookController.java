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

import net.sourceforge.safr.sample.notebook.domain.Entry;
import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @author Martin Krasser
 */
public class DetailNotebookController extends SimpleFormController {

	private NotebookService service;

    /**
     * @return the service
     */
    public NotebookService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(NotebookService service) {
        this.service = service;
    }

    @Override
    protected Map<String, Notebook> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Notebook notebook = getService().findNotebook(request.getParameter("notebookId"));
        Map<String, Notebook> map = new HashMap<String, Notebook>(1);
        map.put("notebook", notebook);
        return map;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Notebook notebook = getService().findNotebook(request.getParameter("notebookId"));
        notebook.addEntry((Entry)command);
        Map map = new HashMap();
        map.put("notebook", notebook);
        map.putAll(errors.getModel());
        return new ModelAndView(getSuccessView(), map);
    }

}
