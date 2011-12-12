package cl.tidev.commons.mvc.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.tidev.commons.helper.ArrayUtils;
import cl.tidev.commons.mvc.service.Service;
import cl.tidev.commons.mvc.service.ServiceManager;
import cl.tidev.commons.mvc.view.JsonView;
import cl.tidev.commons.mvc.view.View;

@SuppressWarnings("unused")
public final class WebController {
	private static final List<String> reserved = new ArrayList<String>();
	
	static{
		reserved.add("mngr");
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<?, ?> processWebMap(Map<?, ?> requestMap){
		Map<Object, Object> response = new HashMap<Object, Object>();
		
		Iterator it = requestMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        if( !reserved.contains(pairs.getKey()) )
	        	response.put(pairs.getKey(), ArrayUtils.extract((String[]) pairs.getValue()));
	    }

		return response;
	}
}
