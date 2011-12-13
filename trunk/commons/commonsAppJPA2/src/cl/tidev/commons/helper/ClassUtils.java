package cl.tidev.commons.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;
import cl.tidev.commons.mvc.service.jpa.ServiceManager;

public final class ClassUtils {

    private static final Logger logger = Logger.getLogger(ClassUtils.class);
    
    public static Object getInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object object = null;
        Class<?> c = Class.forName(className);
        object = c.newInstance();
        return object;
    }

    public static Object getInstance(String packageName, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Object object = null;
        Class<?> c = Class.forName(packageName.concat(className));
        object = c.newInstance();

        return object;
    }

    public static void callMethod(String methodName, Class<?> partypes[], Object arglist[], Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Method m = obj.getClass().getMethod(methodName, partypes);
        m.invoke(obj, arglist);
    }

    public static Object getSingletonInstance(String singletonMethodName, String packageName, String className) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Object object = null;
        Class<?> c = Class.forName(packageName + className);
        Method method = c.getMethod(singletonMethodName);
        object = (Object) method.invoke(null);

        return object;
    }

    @Deprecated
    public static Map<String, Object> getMap(Object instance) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Field[] fields = instance.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Boolean isAccesible = fields[i].isAccessible();
                fields[i].setAccessible(true);
                resultMap.put(fields[i].getName(), fields[i].get(instance));
                fields[i].setAccessible(isAccesible);
            }

        } catch (NullPointerException ex) {
            resultMap.clear();
            resultMap.put("sessionId", null);
            resultMap.put("sessionCreate", null);
            resultMap.put("sessionData", null);
            resultMap.put("sessionModified", null);
            resultMap.put("sessionStatus", null);
        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        } catch (IllegalAccessException ex) {
            logger.error(ex.getMessage());
        }

        return resultMap;
    }/**/


    /** por defecto recibe un String 
     * @throws ParseException 
     * 
     */
    public static Object parseData(Object value, Class<?> field) throws ParseException {
        Object parsedValue = null;
        if (field.equals(String.class) || field.equals(char.class)) {
            parsedValue = value;

        } else if (field.equals(Long.class) || field.equals(long.class)) {
            parsedValue = Long.parseLong((String) value);

        } else if (field.equals(Integer.class) || field.equals(int.class)) {
            parsedValue = Integer.parseInt((String) value);

        } else if (field.equals(Boolean.class) || field.equals(boolean.class)) {
            parsedValue = Boolean.parseBoolean((String) value);

        } else if (field.equals(Double.class) || field.equals(float.class)) {
            parsedValue = Double.parseDouble((String) value);

        } else if (field.equals(BigDecimal.class)) {
            parsedValue = BigDecimal.valueOf(Double.parseDouble((String) value));

        } else if (field.equals(Timestamp.class)) {
            try {
                String date = value.toString().replace("Dic", "Dec").replace("Ene", "Jan").replace("Abr", "Apr").replace("Ago", "Aug").replace("T", " ").concat(".0");

                parsedValue = new Timestamp(((Date) new SimpleDateFormat("dd-MMM-yy", Locale.US).parse(date)).getTime());
            } catch (ParseException e) {
                parsedValue = Timestamp.valueOf((String) value);
            }

        } else {
            AbstractServiceManager<?> serviceManager =
                    ServiceManager.factory(field.getSimpleName().concat("Manager"));

            if (serviceManager.findById(value) != null) {
                parsedValue = serviceManager.findById(value);
            }
        }
        return parsedValue;
    }
}
