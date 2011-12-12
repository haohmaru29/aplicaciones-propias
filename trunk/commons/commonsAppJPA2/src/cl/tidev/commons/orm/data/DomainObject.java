package cl.tidev.commons.orm.data;

import java.lang.reflect.Field;
import java.util.Map;

public final class DomainObject {

	public static Object bindObject(Class<?> c, Map<?, ?> params){
		
		Object object = null;
		
		try {
			object = c.newInstance();
			
			for( int i = 0; i< c.getDeclaredFields().length ; i++){
				Field field = c.getDeclaredFields()[i];
				if( !field.getName().equals("serialVersionUID")) {
					if( params.containsKey(field.getName() )){
						Boolean isPrivate = field.isAccessible();
						field.setAccessible(true);
						field.set(object, params.get( field.getName() ));
						field.setAccessible( isPrivate );
					}
				}
			}
				
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	public static Object bindObject(String className, Map<?, ?> params){
		
		Object object = null;
		
		try {
			Class<?> c = Class.forName(className);
			object = c.newInstance();
			
			Field[] fields = c.getDeclaredFields();
			
			for( int i = 0; i< fields.length ; i++){
				Field field = fields[i];
				Boolean isPrivate = field.isAccessible();
				field.setAccessible(true);
				field.set(object, params.get( field.getName() ));
				field.setAccessible( isPrivate );
			}
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	
}
