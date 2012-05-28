package cl.acgp.commons.mvc.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cl.acgp.commons.helper.JsonUtils;
import cl.acgp.commons.library.json.JsonParser;

public class JsonView extends View {
    
    private static final Logger logger = Logger.getLogger(JsonView.class);

    private StringBuffer jsonResponse = new StringBuffer();

    public void prepareList(List<?> list) {
        if (list != null) {
            jsonResponse.append(JsonParser.parseList(list));
        } else {
            emptyResponse();
        }
    }

    public void prepareResponse(List<?> list, String count) {
        if (list != null) {
            prepareResults(true, JsonParser.parseList(list), count);
        } else {
            emptyResponse();
        }
    }

    @Override
    public void prepareResponse(List<?> list) {
        if (list != null) {
            prepareResults(true, JsonParser.parseList(list));
        } else {
            emptyResponse();
        }
    }

    @Override
    public void prepareResponse(Map<?, ?> map) {
        if (map != null) {
            jsonResponse.append(JsonParser.parseMap(map));
        } else {
            emptyResponse();
        }
    }

    @Override
    public void prepareResponse(String jsonString) {
        jsonResponse.append(jsonString);
    }

    @Override
    public void render(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "no-cache");
        try {
            logger.debug(jsonResponse.toString());
            response.getWriter().print(jsonResponse.toString());
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void render(HttpServletResponse response, String strResponse) {
        response.setContentType("application/json");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "no-cache");
        try {
            logger.debug(strResponse);
            response.getWriter().print(strResponse);
        } catch (IOException ex) {
            logger.error(ex);
        }
    }

    public void emptyResponse() {
        jsonResponse.append("{").append(JsonUtils.parse("success")).append(": true");
        jsonResponse.append(",").append(JsonUtils.parse("message")).append(": \"Datos no encontrados!\"");
        jsonResponse.append(",").append(JsonUtils.parse("data")).append(":[]");
        jsonResponse.append("}");

    }

    public void prepareMessageResponse(Boolean success, String message) {
        jsonResponse.append("{").append(JsonUtils.parse("success")).append(":").append(Boolean.toString(success));
        jsonResponse.append(",").append(JsonUtils.parse("message")).append(":").append(JsonUtils.parse(message));
        jsonResponse.append("}");

    }

    @Override
    public void prepareResponse(Boolean success, String value) {
        jsonResponse.append("{").append(JsonUtils.parse("success")).append(":").append(Boolean.toString(success));
        jsonResponse.append(",").append(JsonUtils.parse("value")).append(":").append(JsonUtils.parse(value));
        jsonResponse.append("}");

    }

    public void prepareResults(Boolean success, String results) {
        jsonResponse.append("{").append(JsonUtils.parse("success")).append(":").append(Boolean.toString(success));
        jsonResponse.append(",").append(JsonUtils.parse("data")).append(":").append(results);
        jsonResponse.append("}");

    }

    public void prepareResults(Boolean success, String results, String count) {
        jsonResponse.append("{").append(JsonUtils.parse("success")).append(":").append(Boolean.toString(success));
        jsonResponse.append(",").append(JsonUtils.parse("count")).append(":").append(count);
        jsonResponse.append(",").append(JsonUtils.parse("data")).append(":").append(results);
        jsonResponse.append("}");
    }

    @Override
    public void prepareResponse(Object object) {
        if (object != null) {
            prepareResults(true, JsonParser.parseClass(object));
        } else {
            prepareMessageResponse(false, "Datos no encontrados!");
        }
    }
}