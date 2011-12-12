package cl.tidev.commons.mvc.repository.jpa;

import java.util.HashMap;

import org.apache.log4j.Logger;

import cl.tidev.commons.mvc.service.jpa.ServiceManager;

public class JpaController{

	private static JpaController instance;
	private static HashMap<String, AbstractJpaController<?>> jpaControllerInstances = 
		new HashMap<String, AbstractJpaController<?>>();

	private JpaController(){
		
	}
	
	public static AbstractJpaController<?> factory(String className){
		if( instance == null)
			instance = new JpaController();
		
		return instance.callController(className);
	}
	
	private AbstractJpaController<?> callController(String className){
		try {
			//Class<?> c = Class.forName(Application.getInstance().repository().concat(className));
			Class<?> c = Class.forName( className );
			if( !jpaControllerInstances.containsKey(className) )
				jpaControllerInstances.put(className, (AbstractJpaController<?>) c.newInstance());
		} catch (InstantiationException e) {
			Logger.getLogger( ServiceManager.class ).error( e.getMessage() );
		} catch (IllegalAccessException e) {
			Logger.getLogger( ServiceManager.class ).error( e.getMessage() );
		} catch (Exception e){
			Logger.getLogger( ServiceManager.class ).error( e.getMessage() );
		}
		
		return jpaControllerInstances.get(className);
	}
	
}
