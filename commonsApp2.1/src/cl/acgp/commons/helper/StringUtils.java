package cl.acgp.commons.helper;

public final class StringUtils {

    public static String[] parseToArray(Object str) {
        String[] arr = {str.toString()};
        return arr;
    }
}