package cl.tidev.commons.mvc.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public abstract class View {
	protected String errorMessage;
	public abstract void render(HttpServletResponse response);
	public abstract void prepareResponse(List<?> list);
	public abstract void prepareResponse(Map<?,?> map);
	public abstract void prepareResponse(Object object);
	public abstract void prepareResponse(String responseString);
	public abstract void prepareResponse(Boolean success, String value);
}
