package cl.intranet.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cl.intranet.domain.ServidorCorreo;
import cl.intranet.domain.Usuario;
import cl.intranet.domain.UsuarioServidorCorreo;
import cl.intranet.service.UsuarioServidorCorreoManager;
import cl.tidev.commons.helper.ArrayUtils;
import cl.tidev.commons.helper.JsonUtils;
import cl.tidev.commons.library.mail.MailEngine;
import cl.tidev.commons.library.mail.SenderMailEngine;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.tidev.commons.mvc.view.extjs.TreeNode;

public class correo {
	private static final Logger logger = Logger.getLogger(correo.class);
	private UsuarioServidorCorreoManager usuarioCorreoManager = 
			(UsuarioServidorCorreoManager) ServiceManager.factory("UsuarioServidorCorreoManager"); 
	
	public void cuentas(HttpServletRequest request, HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		List<UsuarioServidorCorreo>datosCorreo = usuarioCorreoManager.findByUserList(usuario.getIdusuario());
		ServidorCorreo servidorCorreo= null;
		List<String> jsonList = new ArrayList<String>();
		for(UsuarioServidorCorreo servidor: datosCorreo) {
			servidorCorreo=servidor.getServidorCorreo();
			jsonList.add( JsonUtils.jsonObject( TreeNode.getTreeNode(
					servidor.getUsuarioCorreo(), servidor.getIdusuarioServidorCorreo()+"", true, servidorCorreo.getIcono().getNombreIconos())).toString() );
			logger.info(servidor.getIdusuarioServidorCorreo());
		}
		StringBuilder jsonBuffer = new StringBuilder();
		jsonBuffer.append("[").append(ArrayUtils.implode(jsonList, ",")).append("]");
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(jsonBuffer.toString());
		jsonView.render(response);
	}
	
	public void mails(HttpServletRequest request, HttpServletResponse response ) {
		JsonView jsonView = new JsonView();
		MailEngine mailEngine = new MailEngine();
		String idCuenta = request.getParameter("idCuenta");
		int page = Integer.parseInt(request.getParameter("page"));
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		UsuarioServidorCorreo datosCorreo = usuarioCorreoManager.findById(idCuenta);
		List<?> correosRemote = null;
		if("GMAIL".equals(datosCorreo.getServidorCorreo().getNombreServicio())) {
			correosRemote = mailEngine.getGmail(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo(),  page, start, limit);
		} else if ("HOTMAIL".equals(datosCorreo.getServidorCorreo().getNombreServicio())) {
			correosRemote = mailEngine.getHotmail(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo(), page, start, limit);
		} else if("YAHOO".equals(datosCorreo.getServidorCorreo().getNombreServicio())) {
			correosRemote = mailEngine.getYahoo(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo(), page, start, limit);
		} else {
			correosRemote = mailEngine.getGenericMail(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo(), datosCorreo.getServidorCorreo().getIp(), Integer.parseInt(datosCorreo.getServidorCorreo().getPortPop() +""), page, start, limit);
		}
		jsonView.prepareResponse(correosRemote, (mailEngine.getTotal()) +"");
		jsonView.render(response);
	}
	
	public void sent(HttpServletRequest request, HttpServletResponse response ) {
		System.out.println(request.getParameterMap());
		UsuarioServidorCorreo datosCorreo = usuarioCorreoManager.findById(request.getParameter("servidor"));
		ServidorCorreo servidorCorreo = datosCorreo.getServidorCorreo();
		SenderMailEngine sender = new SenderMailEngine(datosCorreo.getUsuarioCorreo(), datosCorreo.getClaveCorreo(), servidorCorreo.getNombreServicio(), Integer.parseInt(servidorCorreo.getPortSmtp()), servidorCorreo.getSmtp());
		sender.to(request.getParameter("to"));
		sender.setSubject(request.getParameter("subject"));
		sender.setContent(request.getParameter("body"), "text/html");
		sender.from(datosCorreo.getUsuarioCorreo());
		JsonView jsonView = new JsonView();
		try {
			sender.send();
			jsonView.prepareResponse(true, "Mensaje enviado correctamente a: <b>" + request.getParameter("to") + "</b>");
		} catch (MessagingException e) {
			logger.error(e);
			jsonView.prepareResponse(true, "Problemas al enviar mensaje <b>" + e.getMessage() + "</b>");
		}
		jsonView.render(response);
	}
	
	public void myaccounts(HttpServletRequest request, HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		List<UsuarioServidorCorreo>datosCorreo = usuarioCorreoManager.findByUserList(usuario.getIdusuario());
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(datosCorreo);
		jsonView.render(response);
	}

	public void newAccount(HttpServletRequest request, HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		JsonView jsonView = new JsonView();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("usuarioCorreo", request.getParameter("usuarioCorreo"));
		m.put("claveCorreo", request.getParameter("claveCorreo"));
		m.put("servidorCorreo", request.getParameter("servidor") );
		m.put("usuario", usuario.getIdusuario() + "");
		usuarioCorreoManager.save(m);
		jsonView.prepareResponse(true, "Cuenta creada con exito");
		jsonView.render(response);
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response ) {
		usuarioCorreoManager.deleteById(request.getParameter("idCuenta"));
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(true, "Cuenta eliminado con exito");
		jsonView.render(response);
	}
	
}
