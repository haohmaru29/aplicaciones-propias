package cl.tidev.commons.mvc.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {

	private Map<Object, Object> cacheMap = new HashMap<Object, Object>();
	
	public Cache() { }
	
	public Object get(Object key){
		return cacheMap.get(key);
	}
	
	public void set(Object key, Object data){
		if( !exist(key) && data != null ){
			cacheMap.put(key, data);
		}
	}
	
	public Boolean exist(Object key){
		return cacheMap.keySet().contains(key);
	}
	
	public void clear(){
		if( !cacheMap.isEmpty() )
			cacheMap.clear();
	}
}
