package cl.acgp.commons.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.acgp.commons.Application;
import cl.acgp.commons.helper.ClassUtils;
import cl.acgp.commons.mvc.cache.Cache;
import cl.acgp.commons.mvc.repository.Controller;

public abstract class ServiceManager {

    private static final Logger logger = Logger.getLogger(ServiceManager.class);
    protected Controller controller;
    protected Map<String, String> response = new HashMap<String, String>();
    protected Cache cache = new Cache();

    public abstract List<?> findById(Object id);

    public abstract List<?> findById(Map<?, ?> params);

    public abstract List<?> findByFields(Map<?, ?> params);

    public abstract void saveOrUpdate(Map<?, ?> params);

    public ServiceManager() {
        loadController();
    }

    public List<?> findAll() {
        return controller.select();
    }

    protected List<?> findByFields(String namedQuery, Map<?, ?> params) {
        return controller.executeNamedQuery(namedQuery, params);
    }

    public void save(Map<?, ?> params) {
        controller.insert(params);
        processResponse();
    }

    public void saveOrUdate(Map<?, ?> params) {
        controller.insertOrUpdate(params);
        processResponse();
    }

    public void delete(Object id) {
        controller.delete(id);
        processResponse();
    }

    public void delete(Map<?, ?> params) {
        controller.delete(params);
        processResponse();
    }

    public void update(Map<?, ?> params) {
        controller.update(params);
        processResponse();
    }

    public Map<?, ?> getMapResponse() {
        return response;
    }

    public String getStringResponse() {
        return response.get("message");
    }

    protected void loadController() {
        try {
            controller = (Controller) ClassUtils.getInstance(Application.getInstance().repository(), this.getClass().getSimpleName().replace("Manager", "Controller"));
        } catch (ClassNotFoundException e) {
            logger.error(e);
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
    }

    private void processResponse() {
        response.clear();
        response.put("success", controller.getSuccess().toString());
        response.put("message", controller.getMessage());

        if (controller.getSuccess()) {
            cache.clear();
        }

    }

    protected Integer getKey(Object param) {
        Integer hashCode = param.hashCode();

        if (hashCode < 0) {
            hashCode *= -1;
        }

        return hashCode;
    }

    public Object getParameter(String paramName) {
        return controller.getParameter(paramName);
    }
}