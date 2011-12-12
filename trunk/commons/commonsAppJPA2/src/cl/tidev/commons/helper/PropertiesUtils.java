package cl.tidev.commons.helper;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public final class PropertiesUtils {

	public static Properties loadProperties(String fileName){
		Properties properties = new Properties();
		
		try {
			properties.load( PropertiesUtils.class.getResourceAsStream( ("/").concat(fileName)) );
		} catch (IOException ex) {
			Logger.getLogger( PropertiesUtils.class ).error( "IOException [loadProperties]: Can't read the properties file => " + fileName );
		}
		
		return properties;
	}
	
	public static ResourceBundle getResource(String resource){
		return ResourceBundle.getBundle(resource);
	}
	
}
