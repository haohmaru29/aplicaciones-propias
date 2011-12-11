package cl.acgp.pdf;

public class Tags {
    public static final String html = "<html";
    public static final String body = "<body";
    public static final String img = "<img";
    public static final String style = "<style";
    public static final String script = "<script";
    public static final String link = "<link";
    
    public static String getType(String type) {
        String response = "";
        if(Tags.link.equals(type)) {
            response = "<style>" + type + "</style>";
        }
        
        return response;
    }
}