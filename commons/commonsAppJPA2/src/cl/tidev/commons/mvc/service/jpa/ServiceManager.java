package cl.tidev.commons.mvc.service.jpa;

import java.util.HashMap;

import org.apache.log4j.Logger;

import cl.tidev.commons.Application;

public class ServiceManager {

	private static ServiceManager instance = null;
	private static HashMap<String, AbstractServiceManager<?>> serviceInstance = 
			new HashMap<String, AbstractServiceManager<?>>();
	
	private ServiceManager(){ }
	
	public static AbstractServiceManager<?> factory(String className){
		if(instance ==  null)
			instance = new ServiceManager();
		
		return instance.callManager(className);
	}
	
	public static AbstractServiceManager<?> factory(Class<?> c){
		if(instance ==  null)
			instance = new ServiceManager();
		
		return instance.callManager(c);
	}
	
	private AbstractServiceManager<?> callManager(Class<?> c){
		String className = c.getSimpleName();
		
		try {
			if( !serviceInstance.containsKey(className) )
				serviceInstance.put(className, (AbstractServiceManager<?>) c.newInstance());
		} catch (InstantiationException e) {
			Logger.getLogger( ServiceManager.class ).error( e );
		} catch (IllegalAccessException e) {
			Logger.getLogger( ServiceManager.class ).error( e );
		} catch (Exception e){
			Logger.getLogger( ServiceManager.class ).error( e );
		}
		
		return serviceInstance.get(className);
	}
	
	private AbstractServiceManager<?> callManager(String className){
		try {
			Class<?> c = Class.forName(Application.getInstance().service().concat(className));
			if( !serviceInstance.containsKey(className) )
				serviceInstance.put(className, (AbstractServiceManager<?>) c.newInstance());
		} catch (InstantiationException e) {
			Logger.getLogger( ServiceManager.class ).error( e );
		} catch (IllegalAccessException e) {
			Logger.getLogger( ServiceManager.class ).error( e );
		} catch (Exception e){
			Logger.getLogger( ServiceManager.class ).error( e );
		}
		
		return serviceInstance.get(className);
	}
}
