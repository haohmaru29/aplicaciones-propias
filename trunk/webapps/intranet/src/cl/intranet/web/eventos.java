package cl.intranet.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.domain.Evento;
import cl.intranet.domain.Usuario;
import cl.intranet.service.EventoManager;
import cl.tidev.commons.helper.ArrayUtils;
import cl.tidev.commons.helper.JsonUtils;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.tidev.commons.mvc.view.extjs.CalendarEvents;

public class eventos {

	private EventoManager eventoManager = 
			(EventoManager) ServiceManager.factory("EventoManager");
	
	public void usuario(HttpServletRequest request, HttpServletResponse response ) {
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		List<Evento> eventos = eventoManager.findNextEventsByUser(usuario.getIdusuario());
		List<String> jsonList = new ArrayList<String>();
		for(Evento evento : eventos) {
			jsonList.add( JsonUtils.jsonObject( CalendarEvents.getEvents(evento.getIdevento(), evento.getCalendario().getIdcalendario(), evento.getFechaInicio(), evento.getFechaTermino(), evento.getTitulo(), evento.getDescripcion())  ).toString() );
			
		}
		
		StringBuilder jsonBuffer = new StringBuilder();
		jsonBuffer.append("[").append(ArrayUtils.implode(jsonList, ",")).append("]");
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(jsonBuffer.toString());
		jsonView.render(response);
	}
	
	public void save(HttpServletRequest request, HttpServletResponse response ) {
		System.out.println("Save Evento" + request.getParameterMap());
		Usuario usuario = (Usuario) request.getSession(false).getAttribute("usuario");
		String id= request.getParameter("id");
		String fechaFin = request.getParameter("end");
		String fechaInicio = request.getParameter("start");
		Map<String, String> params = new HashMap<String, String>();
		
		if(id.equals("0")  ) {
			params.put("idevento", id);
		}
		params.put("descripcion", request.getParameter("notes") );
		params.put("fechaInicio", fechaInicio.substring(0, fechaInicio.lastIndexOf("T")-1 ) );
		params.put("fechaTermino", fechaFin.substring(0, fechaFin.lastIndexOf("T")-1 ) );
		params.put("horaInicio", fechaInicio.substring(fechaInicio.indexOf("T")+1, fechaInicio.length() ) );
		params.put("horaTermino", fechaFin.substring(fechaFin.indexOf("T")+1, fechaFin.length() ) );
		params.put("lugar", request.getParameter("loc") );
		params.put("titulo", request.getParameter("title"));
		params.put("calendario", request.getParameter("cid") );
		params.put("usuario", usuario.getIdusuario() +"");
		eventoManager.save(params );
		
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(true, "Evento guardado con exito");
		jsonView.render(response);
	}
}
