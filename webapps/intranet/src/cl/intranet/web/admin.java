package cl.intranet.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class admin {

	private AbstractServiceManager<?> serviceManager;
	
	public void delete(HttpServletRequest request, HttpServletResponse response ) {
		serviceManager = ServiceManager.factory(request.getParameter("mngr").concat("Manager"));
		serviceManager.deleteById(request.getParameter("id"));
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(true, "Operación realizada correctamente");
		jsonView.render(response);
	}
	
	public void save(HttpServletRequest request, HttpServletResponse response ) {
		System.out.println(request.getParameter("calendario") );
		serviceManager = ServiceManager.factory(request.getParameter("mngr").concat("Manager"));
		request.getParameterMap().remove("mngr");
		serviceManager.save(request.getParameterMap());
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(true, "Operación realizada correctamente");
		jsonView.render(response);
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response ) {
		serviceManager = ServiceManager.factory(request.getParameter("mngr").concat("Manager"));
		serviceManager.update(request.getParameter("id"), request.getParameterMap());
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(true, "Operación realizada correctamente");
		jsonView.render(response);
	}
}
