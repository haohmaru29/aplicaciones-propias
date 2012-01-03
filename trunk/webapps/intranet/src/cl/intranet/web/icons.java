package cl.intranet.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.service.IconoManager;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class icons {
	
	private IconoManager iconoManager = 
			(IconoManager) ServiceManager.factory("IconoManager");
	
	public void mail(HttpServletRequest request, HttpServletResponse response ) {
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(iconoManager.findAll());
		jsonView.render(response);
	}
}
