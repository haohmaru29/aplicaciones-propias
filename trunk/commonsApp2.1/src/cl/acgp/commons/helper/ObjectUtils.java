package cl.acgp.commons.helper;

import java.math.BigDecimal;

public final class ObjectUtils {

    public static Object[] parseToArray(Object object) {
        Object[] arr = {object};
        return arr;
    }

    public static String parseToString(Object value, Class<?> field) {
        String parsedValue = null;

        if (field.equals(String.class) || field.equals(char.class)) {
            parsedValue = (String) value;

        } else if (field.equals(Long.class) || field.equals(long.class)) {
            parsedValue = Long.toString((Long) value);

        } else if (field.equals(Integer.class) || field.equals(int.class)) {
            parsedValue = Integer.toString((Integer) value);

        } else if (field.equals(Boolean.class) || field.equals(boolean.class)) {
            parsedValue = Boolean.toString((Boolean) value);

        } else if (field.equals(Double.class) || field.equals(float.class)) {
            parsedValue = Double.toString((Double) value);

        } else if (field.equals(BigDecimal.class)) {
            parsedValue = value.toString();

        } else {
            parsedValue = value.toString();

        }

        return parsedValue;
    }
}