package cl.acgp.commons.mvc.service.jpa;

import java.util.HashMap;

import org.apache.log4j.Logger;

import cl.acgp.commons.Application;

public class ServiceManager {

    private static final Logger logger = Logger.getLogger(ServiceManager.class);
    private static ServiceManager instance = null;
    private static HashMap<String, AbstractServiceManager<?>> serviceInstance =
            new HashMap<String, AbstractServiceManager<?>>();

    private ServiceManager() {
    }

    public static AbstractServiceManager<?> factory(String className) {
        if (instance == null) {
            instance = new ServiceManager();
        }

        return instance.callManager(className);
    }

    public static AbstractServiceManager<?> factory(Class<?> c) {
        if (instance == null) {
            instance = new ServiceManager();
        }

        return instance.callManager(c);
    }

    private AbstractServiceManager<?> callManager(Class<?> c) {
        String className = c.getSimpleName();

        try {
            if (!serviceInstance.containsKey(className)) {
                serviceInstance.put(className, (AbstractServiceManager<?>) c.newInstance());
            }
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return serviceInstance.get(className);
    }

    private AbstractServiceManager<?> callManager(String className) {
        try {
            Class<?> c = Class.forName(Application.getInstance().service().concat(className));
            if (!serviceInstance.containsKey(className)) {
                serviceInstance.put(className, (AbstractServiceManager<?>) c.newInstance());
            }
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        }

        return serviceInstance.get(className);
    }
}