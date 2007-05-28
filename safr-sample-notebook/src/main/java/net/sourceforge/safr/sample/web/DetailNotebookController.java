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

public class DetailNotebookController extends SimpleFormController {

	private NotebookService service;

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

	@Override
	protected Map<String, Notebook> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
		Notebook notebook = service.findNotebook(request.getParameter("id"));
		Map<String, Notebook> map = new HashMap<String, Notebook>(1);
		map.put("notebook", notebook);
		return map;
	}

    @Override
    @SuppressWarnings("unchecked")
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Notebook notebook = service.findNotebook(request.getParameter("id"));
        notebook.addEntry((Entry)command);
        Map map = new HashMap();
        map.put("notebook", notebook);
        map.putAll(errors.getModel());
        return new ModelAndView(getSuccessView(), map);
    }

}
