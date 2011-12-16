package cl.tidev.commons;

import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import cl.tidev.commons.helper.ClassUtils;
import cl.tidev.commons.helper.PropertiesUtils;

public class Application {
 
	private static final String repositoryPackage = "module.package.repository";
	private static final String webPackage = "module.package.web";
	private static final String appPackage = "module.package.application";
	private static final String domainPackage = "module.package.domain";
	private static final String modulePackage = "module.package";
	private static final String servicePackage = "module.package.service";
	private static final String moduleName = "module.name";
	private static final String chartPath = "module.chart.path";
	
	private static Application instance = new Application();
	private Properties appProperties = new Properties();

	public static HashMap<String, Object> instantiatedClasses = new HashMap<String, Object>();
	
	private Application(){ 
		appProperties = PropertiesUtils.loadProperties("module.properties"); 
	}
	
	public static Application getInstance() { return instance; }
	
	public String web(){ 
		return appProperties.getProperty( webPackage ).concat("."); 
	}
	
	public String domain(){ 
		return appProperties.getProperty( domainPackage ).concat("."); 
	}
	
	public String repository(){ 
		return appProperties.getProperty( repositoryPackage ).concat("."); 
	}
	
	public String module(){ 
		return appProperties.getProperty( modulePackage ).concat("."); 
	}
	
	public String service(){ 
		return appProperties.getProperty( servicePackage ).concat("."); 
	}
	
	public String app(){
		return appProperties.getProperty( appPackage ).concat("."); 
	}
	
	public String name(){ 
		return appProperties.getProperty( moduleName ).concat("."); 
	}
	
	public String charts(){ 
		return appProperties.getProperty( chartPath ); 
	}
	
	public synchronized Object getAppClass(String className){
		if( !instantiatedClasses.containsKey( className ))
			try {
				instantiatedClasses.put( className , ClassUtils.getInstance( this.app() , className));
			} catch (ClassNotFoundException e) {
				Logger.getLogger( Application.class).error(e.getMessage());
			} catch (InstantiationException e) {
				Logger.getLogger( Application.class).error(e.getMessage());
			} catch (IllegalAccessException e) {
				Logger.getLogger( Application.class).error(e.getMessage());
			}
		
		return instantiatedClasses.get( className );
	}
	
}
