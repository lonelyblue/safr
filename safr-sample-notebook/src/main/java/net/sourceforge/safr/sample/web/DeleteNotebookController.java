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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @author Martin Krasser
 */
public class DeleteNotebookController extends AbstractController {

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
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("notebookId");
		Notebook notebook = getService().findNotebook(id);
		service.deleteNotebook(notebook);
		return new ModelAndView("notebookList", "notebooks", getService().findNotebooks());
	}

}
