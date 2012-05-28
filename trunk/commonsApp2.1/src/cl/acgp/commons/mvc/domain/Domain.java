package cl.acgp.commons.mvc.domain;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Domain {

	public static Map<Object, Object> getMap(Object obj){
		Map<Object, Object> params = new HashMap<Object, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		
		try {
			for( int i = 0; i< fields.length ; i++){
				Field field = fields[i];
				Boolean isAccesible = field.isAccessible();
				field.setAccessible(true);
				params.put(fields[i].getName(), fields[i].get(obj));
				fields[i].setAccessible(isAccesible);
			}
				
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(obj.getClass()).error( ex.getMessage());
		} catch (IllegalAccessException ex) {
			Logger.getLogger(obj.getClass()).error( ex.getMessage());
		}
		
		return params;
	}
	
}
