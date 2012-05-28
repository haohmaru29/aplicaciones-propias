package cl.acgp.commons.helper;

import java.util.List;

import org.apache.log4j.Logger;

public final class ArrayUtils {

    private static final Logger logger = Logger.getLogger(ArrayUtils.class);
    
    public static String extract(Object data) {
    	String str = null;
    	try {
    		Object[] d = (Object[])data;
    		str = (String)d[0];
    	} catch (Exception ex) {
    		str = data.toString();
    	}
    	 
    	return str;
   }
    
    public static String extract(String[] data) {
        String str = null;
        try {
            str = data[0];
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException [extract]: " + ex.getMessage());
        }

        return str;
    }

    public static ObjectUtils extract(ObjectUtils[] data) {
        ObjectUtils str = null;

        try {
            str = data[0];
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException [extract]: " + ex.getMessage());
        }

        return str;
    }

    public static String implode(String[] ary, String delim) {
        String out = "";
        for (int i = 0; i < ary.length; i++) {
            if (i != 0) {
                out += delim;
            }
            out += ary[i];
        }
        return out;
    }

    public static String implode(List<?> data, String delimiter) {
        StringBuilder buffer = new StringBuilder();
        int cont = 0;
        for (Object ob : data) {
            if (cont != 0) {
                buffer.append(delimiter).append(ob.toString());
            } else {
                buffer.append(ob.toString());
            }
            cont++;
        }
        return buffer.toString();
    }
}