package cl.tidev.commons.mvc.service;

import java.util.HashMap;

import cl.tidev.commons.Application;
import cl.tidev.commons.helper.ClassUtils;

public final class Service {
	public static HashMap<String, ServiceManager> instantiatedClasses = new HashMap<String, ServiceManager>();
	
	public static ServiceManager callManager(String className){
		registerInstance( className );
		return getInstance( className );
	}
	
	private static ServiceManager getInstance(String className){
		return (ServiceManager) instantiatedClasses.get( className );
	}
	
	private static void registerInstance(String className){
		if( !instantiatedClasses.containsKey( className ) )
			instantiatedClasses.put(className, makeInstance( className ));
		
	}
	
	private static ServiceManager makeInstance(String className) {
		try {
			return (ServiceManager) ClassUtils.getInstance(
					Application.getInstance().service(), className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
