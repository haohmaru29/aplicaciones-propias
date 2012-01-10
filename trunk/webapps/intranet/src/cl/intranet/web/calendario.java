package cl.intranet.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.domain.Usuario;
import cl.intranet.domain.UsuarioCalendario;
import cl.intranet.service.UsuarioCalendarioManager;
import cl.tidev.commons.helper.ArrayUtils;
import cl.tidev.commons.helper.JsonUtils;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;

public class calendario {

	private UsuarioCalendarioManager calendarioManager = 
			(UsuarioCalendarioManager) ServiceManager.factory("UsuarioCalendarioManager");
	
	public void usuario(HttpServletRequest request , HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		List<UsuarioCalendario> calendarios = calendarioManager.findCalendarByUser(usuario.getIdusuario());
		List<String> jsonList = new ArrayList<String>();
		Map<String, Object> m = null;
		for(UsuarioCalendario calendario : calendarios) {
			m = new HashMap<String, Object>();
			m.put("idCalendario", calendario.getCalendario().getIdcalendario()  );
			m.put("nombreCalendario", calendario.getCalendario().getNombreCalendario() );
			jsonList.add( JsonUtils.jsonObject( m ).toString() );
			
		}
		StringBuilder jsonBuffer = new StringBuilder();
		jsonBuffer.append("[").append(ArrayUtils.implode(jsonList, ",")).append("]");
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(jsonBuffer.toString());
		jsonView.render(response);
	}
}
