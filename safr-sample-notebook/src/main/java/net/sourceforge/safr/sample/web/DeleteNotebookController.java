package net.sourceforge.safr.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class DeleteNotebookController extends AbstractController {

	private NotebookService service;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Notebook notebook = service.findNotebook(id);
		service.deleteNotebook(notebook);
		return new ModelAndView("list");
	}

	/**
	 * @return the service
	 */
	public NotebookService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(NotebookService service) {
		this.service = service;
	}

}
