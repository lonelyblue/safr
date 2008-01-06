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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.sample.notebook.domain.Notebook;

import org.springframework.web.servlet.ModelAndView;

/**
 * @author Martin Krasser
 */
public class DeleteEntryController extends DetailNotebookController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String notebookId = request.getParameter("notebookId");
        String entryId = request.getParameter("entryId");
        Notebook notebook = getNotebookService().findNotebook(notebookId);
        notebook.removeEntry(entryId);
        return super.handleRequestInternal(request, response);
    }

}
