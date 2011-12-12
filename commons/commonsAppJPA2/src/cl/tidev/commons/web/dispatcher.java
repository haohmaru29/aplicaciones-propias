package cl.tidev.commons.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cl.tidev.commons.Application;
import cl.tidev.commons.helper.ClassUtils;
import cl.tidev.commons.mvc.view.JsonView;

/**
 * Servlet implementation class dispatcher
 */
public class dispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, Object> controllerClass = new HashMap<String, Object>();
	
	private static List<String> globalController = new ArrayList<String>();
	
	static{
		globalController.add("connection");
		globalController.add("session");
		globalController.add("security");
		globalController.add("document");
	}
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public dispatcher() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return super.getServletInfo();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processResponse(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processResponse(request, response);
	}

	/**
	 * @see HttpServlet#doHead(HttpServletRequest, HttpServletResponse)
	 */
	protected void doHead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doHead(request, response);
	}

	public void processResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String methodName = request.getParameter("m").trim();
        String className = request.getParameter("c").trim();
        String webPath = Application.getInstance().web();
        
        request.getParameterMap().remove("m");
        request.getParameterMap().remove("c");
        
        JsonView jsonView = new JsonView();
        
		if( globalController.contains( className ))
			webPath = "cl.ejedigital.commons.web.";
        
		try {
			ClassUtils.callMethod(methodName, new Class[]{HttpServletRequest.class, HttpServletResponse.class} 
					, new Object[]{request, response}, getClassInstance( webPath, className ));
		} catch (SecurityException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		} catch (IllegalArgumentException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		} catch (NoSuchMethodException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		} catch (IllegalAccessException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		} catch (InvocationTargetException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		} catch (ClassNotFoundException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		} catch (InstantiationException e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response); 
		} catch(Exception e) {
			Logger.getLogger( dispatcher.class).error("Dispatcher Error -> ", e);
			jsonView.prepareMessageResponse(false, e.getMessage());
			jsonView.render(response);
		}
		
		
	}
	
	public Object getClassInstance(String _package, String controller) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		if( !dispatcher.controllerClass.containsKey( controller )){
			dispatcher.controllerClass.put( controller , ClassUtils.getInstance(_package, controller));
		}
		
		return dispatcher.controllerClass.get( controller );
	}
}
