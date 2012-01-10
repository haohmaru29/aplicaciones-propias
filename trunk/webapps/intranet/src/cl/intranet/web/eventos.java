package cl.intranet.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.domain.Usuario;
import cl.intranet.service.EventoManager;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class eventos {

	private EventoManager eventoManager = 
			(EventoManager) ServiceManager.factory("EventoManager");
	
	public void usuario(HttpServletRequest request, HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario"); 
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(eventoManager.findNextEventsByUser(usuario.getIdusuario()));
		jsonView.render(response);
	}
}
