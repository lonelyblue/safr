package net.sourceforge.safr.sample.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.safr.sample.notebook.domain.Notebook;
import net.sourceforge.safr.sample.notebook.service.NotebookService;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class DetailNotebookController extends SimpleFormController {

	private NotebookService service;




	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest, java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	protected Map<String, Notebook> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		Notebook notebook = service.findNotebook(request.getParameter("id"));
		Map<String, Notebook> map = new HashMap<String, Notebook>(1);
		map.put("notebook", notebook);
		return map;
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
