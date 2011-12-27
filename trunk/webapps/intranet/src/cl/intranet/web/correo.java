package cl.intranet.web;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cl.intranet.domain.Usuario;
import cl.intranet.domain.UsuarioServidorCorreo;
import cl.intranet.service.UsuarioServidorCorreoManager;
import cl.tidev.commons.library.mail.Gmail;
import cl.tidev.commons.library.mail.SenderMailEngine;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class correo {
	private static final Logger logger = Logger.getLogger(correo.class);
	private UsuarioServidorCorreoManager usuarioCorreoManager = 
			(UsuarioServidorCorreoManager) ServiceManager.factory("UsuarioServidorCorreoManager"); 
	
	public void cuentas(HttpServletRequest request, HttpServletResponse response ) {
		
	}
	
	public void mails(HttpServletRequest request, HttpServletResponse response ) {
		JsonView jsonView = new JsonView();
		Gmail gmail = new Gmail();
		int page = Integer.parseInt(request.getParameter("page"));
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		UsuarioServidorCorreo datosCorreo = usuarioCorreoManager.findByUser(usuario.getIdusuario());
		List<?> correosRemote = gmail.readGmail(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo(), "INBOX", page, start, limit);
		jsonView.prepareResponse(correosRemote, (gmail.getTotal()) +"");
		jsonView.render(response);
	}
	
	public void sent(HttpServletRequest request, HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		UsuarioServidorCorreo datosCorreo = usuarioCorreoManager.findByUser(usuario.getIdusuario());
		SenderMailEngine sender = new SenderMailEngine(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo());
		String to = request.getParameter("to");
		String subject = request.getParameter("subject");
		String body = request.getParameter("body");
		sender.to(to);
		sender.setSubject(subject);
		sender.setContent(body, "");
		try {
			sender.send();
		} catch (MessagingException e) {
			logger.error(e);
		}
	}
}
