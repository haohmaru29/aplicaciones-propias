package cl.intranet.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.service.ServidorCorreoManager;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class servidor {
	
	private ServidorCorreoManager servidorManager = 
			(ServidorCorreoManager) ServiceManager.factory("ServidorCorreoManager");
	
	public void all(HttpServletRequest request, HttpServletResponse response ) {
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(servidorManager.findAll());
		jsonView.render(response);
		
	}
}
