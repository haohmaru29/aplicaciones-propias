package cl.tidev.commons.helper;

import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

public final class ArrayUtils {
	
	public static String extract( String[] data){
		String str = null;
		
		try{
			str = data[0];
		} catch(IllegalArgumentException ex){
			Logger.getLogger( ArrayUtils.class ).error( "IllegalArgumentException [extract]: " + ex.getMessage() );
		}
		
		return str;
	}
	
	public static ObjectUtils extract( ObjectUtils[] data){
		ObjectUtils str = null;
		
		try{
			str =  data[0];
		} catch(IllegalArgumentException ex){
			Logger.getLogger( ArrayUtils.class ).error( "IllegalArgumentException [extract]: " + ex.getMessage() );
		}
		
		return str;
	}
	
	public static String implode(String[] ary, String delim) {
	    String out = "";
	    for(int i=0; i<ary.length; i++) {
	        if(i!=0) { out += delim; }
	        out += ary[i];
	    }
	    return out;
	}
	
	public static String implode(List<?> data, String delimiter){
		StringBuilder buffer = new StringBuilder();
		ListIterator<?> dataIterator = data.listIterator();
		
		if( !data.isEmpty()) buffer.append(dataIterator.next() );
		for(Object ob: data) {
			buffer.append( delimiter ).append(ob.toString());
		}
		
		return buffer.toString();
	}
}
