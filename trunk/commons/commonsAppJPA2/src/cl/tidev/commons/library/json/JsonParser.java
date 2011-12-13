package cl.tidev.commons.library.json;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.tidev.commons.helper.ArrayUtils;
import cl.tidev.commons.helper.DateUtils;
import cl.tidev.commons.helper.JpaUtils;
import cl.tidev.commons.helper.JsonUtils;

public class JsonParser {

    public static Boolean success = false;

    public static String parseClass(Object obj) {
        List<String> jsonData = new ArrayList<String>();
        Field[] fields = obj.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                Boolean isAccesible = field.isAccessible();
                field.setAccessible(true);

                if (field.get(obj) != null) {
                    if (!JpaUtils.isToManyAnnotation(field)) {
                        jsonData.add(new StringBuilder().append(JsonUtils.parse(field.getName())).append(":").append(parseValue(field, obj)).toString());
                    }
                } else {
                    jsonData.add(new StringBuilder().append(JsonUtils.parse(field.getName())).append(":").append("\"\"").toString());
                }

                field.setAccessible(isAccesible);
            }

        } catch (IllegalArgumentException ex) {
            Logger.getLogger(obj.getClass()).error(ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(obj.getClass()).error(ex);
        }

        return new StringBuilder().append("{").append(ArrayUtils.implode(jsonData, ", ")).append("}").toString();
    }

    private static String parseValue(Class<?> dataType, Object value) {
        Object parsedValue = null;

        if (value == null) {
            parsedValue = "null";

        } else if (dataType.equals(List.class)) {
            parsedValue = parseList((List<?>) value);

        } else if (dataType.equals(Map.class)) {
            parsedValue = parseMap((Map<?, ?>) value);

        } else if (dataType.equals(HashMap.class)) {
            parsedValue = parseMap((Map<?, ?>) value);
        } else if (dataType.equals(Timestamp.class)) {
            parsedValue = "";
            if (value != null) {
                parsedValue = JsonUtils.parse(DateUtils.parseTimestamp(value.toString()));
            }
        } else if (dataType.equals(String.class)
                || dataType.equals(Long.class)
                || dataType.equals(long.class)
                || dataType.equals(Integer.class)
                || dataType.equals(int.class)
                || dataType.equals(short.class)
                || dataType.equals(Boolean.class)
                || dataType.equals(boolean.class)
                || dataType.equals(BigDecimal.class)
                || dataType.equals(Object.class)) {
            parsedValue = JsonUtils.parse(value);

        } else if (dataType.equals(Object.class)) {
        } else {
            if (value.getClass().isInstance(value)) {
                parsedValue = parseClass(value);
            } else {
                parsedValue = "null";
            }
        }

        return parsedValue.toString();
    }

    public static String parseField(Field field, Object instance) {
        try {
            return parseValue(field, instance);
        } catch (IllegalArgumentException e) {
            Logger.getLogger(JsonParser.class).error(e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(JsonParser.class).error(e);
        }
        return null;
    }

    private static String parseValue(Field field, Object instance) throws IllegalArgumentException, IllegalAccessException {
        Object parsedValue = null;
        Class<?> dataType = field.getType();

        Boolean isAccessible = field.isAccessible();

        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        Object value = field.get(instance);

        if (!JpaUtils.isToManyAnnotation(field)) {
            if (value == null) {
                parsedValue = "null";

            } else if (dataType.equals(List.class)) {
                parsedValue = parseList((List<?>) value);

            } else if (dataType.equals(Map.class)) {
                parsedValue = parseMap((Map<?, ?>) value);

            } else if (dataType.equals(Timestamp.class)) {
                parsedValue = "";
                if (value != null) {
                    parsedValue = JsonUtils.parse(DateUtils.parseTimestamp(value.toString()));
                }

            } else if (dataType.equals(String.class)
                    || dataType.equals(Long.class)
                    || dataType.equals(long.class)
                    || dataType.equals(Integer.class)
                    || dataType.equals(int.class)
                    || dataType.equals(short.class)
                    || dataType.equals(Boolean.class)
                    || dataType.equals(boolean.class)
                    || dataType.equals(BigDecimal.class)) {
                parsedValue = JsonUtils.parse(value);

            } else {
                if (value.getClass().isInstance(value)) {
                    parsedValue = parseClass(value);
                } else {
                    parsedValue = "null";
                }
            }
        } else {
            parsedValue = "[]";
        }

        field.setAccessible(isAccessible);

        return parsedValue.toString();
    }

    public static String parseMap(Map<?, ?> map) {
        List<String> jsonData = new ArrayList<String>();
        Iterator<?> keysIterator = map.keySet().iterator();

        String key = null;

        while (keysIterator.hasNext()) {
            key = (String) keysIterator.next();

            if (map.get(key) != null) {
                jsonData.add(new StringBuilder().append(JsonUtils.parse(key)).append(":").append(parseValue(map.get(key).getClass(), map.get(key))).toString());
            } else {
                jsonData.add(new StringBuilder().append(JsonUtils.parse(key)).append(":").append("\"\"").toString());
            }

        }

        return new StringBuilder().append("{").append(ArrayUtils.implode(jsonData, ",")).append("}").toString();
    }

    public static String parseList(List<?> list) {
        List<String> jsonData = new ArrayList<String>();
        for (Object e : list) {
            jsonData.add(parseValue(e.getClass(), e));
        }

        return new StringBuilder().append("[").append(ArrayUtils.implode(jsonData, ", ")).append("]").toString();
    }
}
