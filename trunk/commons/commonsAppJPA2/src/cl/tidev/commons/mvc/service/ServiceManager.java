package cl.tidev.commons.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.tidev.commons.Application;
import cl.tidev.commons.helper.ClassUtils;
import cl.tidev.commons.mvc.cache.Cache;
import cl.tidev.commons.mvc.repository.Controller;

public abstract class ServiceManager{
	protected Controller controller;
	protected Map<String, String> response = new HashMap<String, String>();
	
	protected Cache cache = new Cache();
	
	/*private static final Map<Class<?>, ServiceManager> singletonRegistry = new HashMap<Class<?>, ServiceManager>();
	
    public static final synchronized ServiceManager getInstance(Class<?> cls) {
    	ServiceManager instance = singletonRegistry.get(cls);
        if (instance == null) {
            if (!ServiceManager.class.isAssignableFrom(cls))
                throw new IllegalArgumentException("Singleton: getInstance: Class " + cls.getName() + " is not a subclass of AbstractSingleton?.");
            try {
                instance = (ServiceManager)cls.newInstance();
            }
            catch(Exception ex) {
                System.out.println("Singleton: getInstance: Could not instantiate object for " + cls.getName() + ": " + ex.getMessage());
            }
            if (instance != null) {
                singletonRegistry.put(cls, instance);
                System.out.println("Singleton: getInstance: Registered singleton " + cls.getName() + ".");
            }
            else
                System.out.println("Singleton: getInstance: Could not register singleton " + cls.getName() + ".");
        }
        return instance;
    }

	*/
	public abstract List<?> findById(Object id);
	public abstract List<?> findById(Map<?,?> params);
	public abstract List<?> findByFields(Map<?,?> params);
	public abstract void saveOrUpdate(Map<?,?> params);
	
	public ServiceManager(){
		loadController();
	}
	
	public List<?> findAll(){
		return controller.select();
	}
	
	protected List<?> findByFields(String namedQuery, Map<?,?> params){
		return controller.executeNamedQuery(namedQuery, params);
	}
	
	public void save(Map<?,?> params){
		controller.insert(params);
		processResponse();
	}
	
	public void saveOrUdate(Map<?,?> params){
		controller.insertOrUpdate(params);
		processResponse();
	}
	
	public void delete(Object id){
		controller.delete(id);
		processResponse();
	}
	
	public void delete(Map<?,?> params){
		controller.delete(params);
		processResponse();
	}
	
	public void update(Map<?,?> params){
		controller.update(params);
		processResponse();
	}
	
	public Map<?,?> getMapResponse(){
		return response;
	}
	
	public String getStringResponse(){
		return response.get("message");
	}	
	
	
	protected void loadController(){
		try {
			controller = (Controller) ClassUtils.getInstance(Application.getInstance().repository(), this.getClass().getSimpleName().replace("Manager", "Controller"));
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
	}
	
	private void processResponse(){
		response.clear();
		response.put("success", controller.getSuccess().toString());
		response.put("message", controller.getMessage() );
		
		if( controller.getSuccess() ) cache.clear();
		
	}
	
	protected Integer getKey(Object param){
		Integer hashCode = param.hashCode();
		
		if( hashCode < 0) hashCode *= -1;
		
		return hashCode;
	}
	
	public Object getParameter(String paramName){
		return controller.getParameter(paramName);
	}
}
