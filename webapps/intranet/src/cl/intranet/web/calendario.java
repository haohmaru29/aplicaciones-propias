package cl.intranet.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.intranet.domain.Calendario;
import cl.intranet.service.CalendarioManager;
import cl.tidev.commons.helper.ArrayUtils;
import cl.tidev.commons.helper.JsonUtils;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.tidev.commons.mvc.view.extjs.CalendarEvents;

public class calendario {

	private CalendarioManager calendarioManager = 
			(CalendarioManager) ServiceManager.factory("CalendarioManager");
	
	public void usuario(HttpServletRequest request , HttpServletResponse response ) {
		List<String> jsonList = new ArrayList<String>();
		List<Calendario> calendarios = calendarioManager.findAll();
		
		for(Calendario calendar : calendarios) {
			jsonList.add( JsonUtils.jsonObject(CalendarEvents.getCalendar(calendar.getIdcalendario(), calendar.getNombreCalendario(), calendar.getColor() + "")).toString() );
		}
		
		StringBuilder jsonBuffer = new StringBuilder();
		jsonBuffer.append("[").append(ArrayUtils.implode(jsonList, ",")).append("]");
		JsonView jsonView = new JsonView();
		jsonView.prepareResponse(jsonBuffer.toString());
		jsonView.render(response);
		
	}
}
