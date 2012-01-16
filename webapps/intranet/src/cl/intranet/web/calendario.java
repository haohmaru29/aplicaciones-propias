package cl.intranet.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.service.CalendarioManager;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class calendario {

	private CalendarioManager calendarioManager = 
			(CalendarioManager) ServiceManager.factory("CalendarioManager");
	
	public void usuario(HttpServletRequest request , HttpServletResponse response ) {
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(calendarioManager.findAll() );
		jsonView.render(response);
	}
}
