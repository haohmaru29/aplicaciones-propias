package cl.acgp.commons.mvc.service.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.acgp.commons.helper.ClassUtils;
import cl.acgp.commons.helper.JpaUtils;
import cl.acgp.commons.mvc.cache.Cache;
import cl.acgp.commons.mvc.repository.jpa.AbstractJpaController;
import cl.acgp.commons.mvc.repository.jpa.JpaController;

public abstract class AbstractServiceManager<T> {

    protected AbstractJpaController<T> jpaController;
    protected Class<T> entityClass;
    protected Cache cache = new Cache();
    protected List<T> objectResponse;
    protected List<Map<?, ?>> mapResponse;
    private static final Logger logger = Logger.getLogger(AbstractServiceManager.class);

    @SuppressWarnings("unchecked")
    public AbstractServiceManager() {
        try {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            entityClass = (Class<T>) thisType.getActualTypeArguments()[0];
            String controllerName = entityClass.getName().concat("Controller").replace("domain", "repository");
            jpaController = (AbstractJpaController<T>) JpaController.factory(controllerName);
        } catch (SecurityException e) {
            logger.error(e.getMessage());
        }
    }

    public T findById(Object id) {
        T entity = null;
        try {
            entity = jpaController.findById(ClassUtils.parseData(id, getIdFieldType()));
        } catch (ParseException e) {
            logger.error(e);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return (List<T>) ((ArrayList<T>) jpaController.findAll(0)).clone();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByAttributes(Map<String, Object> params) {
        return (List<T>) ((ArrayList<T>) jpaController.findByAttributes(params)).clone();
    }
    
    public T save(T entity) {
    	return jpaController.save(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByAttribute(String attribute, Object value) {
        Object finalValue = null;
        List<T> results = null;

        try {
            Field field = entityClass.getDeclaredField(attribute);
            finalValue = ClassUtils.parseData(value, field.getType());

            results = (List<T>) ((ArrayList<T>) jpaController.findByProperty(attribute, finalValue)).clone();
        } catch (SecurityException e) {
            logger.error(e);
        } catch (NoSuchFieldException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }

        return results;
    }

    /**
     * 
     * @param params from getParamameterMap() Servlet
     * @return
     */
    public T save(Map<?, ?> params) {
        return jpaController.save(getEntity(params));
    }

    public void delete(T entity) {
        jpaController.delete(entity);
    }

    public void deleteById(Object id) {
        try {
            jpaController.deleteById(ClassUtils.parseData(id, getIdFieldType()));
        } catch (ParseException e) {
            logger.error(e);
        }
    }

    public T getEntityReference(Object id) {
        return jpaController.getEntityReference(id);
    }

    public T update(Object id, Map<?, ?> params) {
        T entity = null;
        try {
            entity = jpaController.update(ClassUtils.parseData(id, getIdFieldType()), params);
        } catch (ParseException e) {
            logger.error(e);
        }

        return entity;
    }
    
    public T update(T entity) {     
    	return this.jpaController.update(entity);  
    }

    protected Class<?> getIdFieldType() {
        Class<?> idField = null;
        Field[] fields = entityClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (JpaUtils.isIdAnnotation(field)) {
                idField = field.getType();
                break;
            }
        }

        return idField;
    }

    protected String getIdField() {
        String idField = null;
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field : fields ) {
            if (JpaUtils.isIdAnnotation(field)) {
                idField = field.getName();
                break;
            }
        }

        return idField;
    }

    @SuppressWarnings("rawtypes")
    public T getEntity(Map<?, ?> data) {
        T entity;
        try {
            entity = entityClass.newInstance();
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                Field field = entity.getClass().getDeclaredField((String) pairs.getKey());
                Boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                if (pairs.getValue() != null && !pairs.getValue().equals("")) {
                    field.set(entity, ClassUtils.parseData(pairs.getValue(), field.getType()));
                } else {
                    field.set(entity, null);
                }

                field.setAccessible(isAccessible);
            }

            return entity;
        } catch (SecurityException e) {
            logger.error(e);
        } catch (NoSuchFieldException e) {
            logger.error(e);
        } catch (IllegalArgumentException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }
        return null;

    }

    @SuppressWarnings("rawtypes")
    protected T populate(T entity, Map<?, ?> data) {

        try {
            Iterator it = data.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry) it.next();
                Field field = entity.getClass().getDeclaredField((String) pairs.getKey());
                Boolean isAccessible = field.isAccessible();
                field.setAccessible(true);

                if (pairs.getValue() != null && !pairs.getValue().equals("")) {
                    field.set(entity, ClassUtils.parseData(pairs.getValue(), field.getType()));
                } else {
                    field.set(entity, null);
                }

                field.setAccessible(isAccessible);
            }

            return entity;
        } catch (SecurityException e) {
            logger.error(e);
        } catch (NoSuchFieldException e) {
            logger.error(e);
        } catch (IllegalArgumentException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (ParseException e) {
            logger.error(e);
        }
        return null;
    }

    public List<?> getObjectResponse() {
        return objectResponse;
    }
}