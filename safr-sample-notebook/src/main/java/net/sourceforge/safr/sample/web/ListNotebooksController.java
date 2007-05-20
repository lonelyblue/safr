package net.sourceforge.safr.sample.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class ListNotebooksController extends AbstractController {

	private NotebookService service;
	
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) {
		Collection<Notebook> notebooks = service.findNotebooks();
		return new ModelAndView("notebookList", "notebooks", notebooks);
	}

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



}
