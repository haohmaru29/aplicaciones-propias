package cl.tidev.commons.mvc.service.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.tidev.commons.helper.ClassUtils;
import cl.tidev.commons.helper.JpaUtils;
import cl.tidev.commons.mvc.cache.Cache;
import cl.tidev.commons.mvc.repository.jpa.AbstractJpaController;
import cl.tidev.commons.mvc.repository.jpa.JpaController;

public abstract class AbstractServiceManager<T> {

	protected AbstractJpaController<T> jpaController;
	protected Class<T> entityClass;
	protected Cache cache = new Cache();
	protected List<T> objectResponse;
	protected List<Map<?, ?>> mapResponse;
	
	@SuppressWarnings("unchecked")
	public AbstractServiceManager(){
		try {
        	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
        	entityClass = (Class<T>) thisType.getActualTypeArguments()[0];
        	String controllerName = entityClass.getName().concat("Controller").replace("domain", "repository");
        	//jpaController = (AbstractJpaController<T>) JpaController.factory( entityClass.getSimpleName().concat("Controller"));
        	jpaController = (AbstractJpaController<T>) JpaController.factory( controllerName );
		} catch (SecurityException e) {
			Logger.getLogger( getClass() ).error(e.getMessage());
		} 
	}
	
	public T findById(Object id){
		T entity = null;
		try {
			entity = jpaController.findById( ClassUtils.parseData(id, getIdFieldType()) );
		} catch (ParseException e) {
			Logger.getLogger( getClass() ).error(e);
		}
		
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		return (List<T>) ((ArrayList<T>) jpaController.findAll(0)).clone();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> findByAttributes(Map<String, Object> params){
		return (List<T>) ((ArrayList<T>) jpaController.findByAttributes(params)).clone();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByAttribute(String attribute, Object value){
		Object finalValue = null;
		List<T> results = null;
		
		try {
			Field field = entityClass.getDeclaredField(attribute);
			finalValue = ClassUtils.parseData(value, field.getType());
			
			results = (List<T>) ((ArrayList<T>) jpaController.findByProperty(attribute, finalValue)).clone();
		} catch (SecurityException e) {
			Logger.getLogger( getClass() ).error( e);
		} catch (NoSuchFieldException e) {
			Logger.getLogger( getClass() ).error( e);
		} catch (ParseException e) {
			Logger.getLogger( getClass() ).error( e);
		}
		
		return results;
	}
	
	/**
	 * 
	 * @param params from getParamameterMap() Servlet
	 * @return
	 */
	public T save(Map<?, ?> params){
		return jpaController.save( getEntity(params) );
	}
	
	public void delete(T entity){
		jpaController.delete(entity);
	}
	
	public void deleteById(Object id){
		try {
			jpaController.deleteById(ClassUtils.parseData(id, getIdFieldType()));
		} catch (ParseException e) {
			Logger.getLogger(getClass()).error(e);
		}
	}
	
	public T getEntityReference(Object id){
    	return jpaController.getEntityReference(id);
    }
	
	public T update(Object id, Map<?, ?> params){
		T entity = null;
		try {
			entity = jpaController.update( ClassUtils.parseData(id, getIdFieldType()), params );
		} catch (ParseException e) {
			Logger.getLogger(getClass()).error(e);
		}
		
		return entity;
    }
	
	protected Class<?> getIdFieldType(){
		Class<?> idField = null;
		Field[] fields = entityClass.getDeclaredFields();
		
		for(int i = 0; i< fields.length ; i ++){
			Field field = fields[i];
			if( JpaUtils.isIdAnnotation(field) ) {
				idField = field.getType();
				break;
			}
		}
		
		return idField;
	}
	
	protected String getIdField(){
		String idField = null;
		Field[] fields = entityClass.getDeclaredFields();
		
		for(int i = 0; i< fields.length ; i ++){
			Field field = fields[i];
			if( JpaUtils.isIdAnnotation(field) ) {
				idField = field.getName();
				break;
			}
		}
		
		return idField;
	}
	
	@SuppressWarnings("rawtypes")
	public T getEntity(Map<?, ?> data){
		T entity;
		
		try {
			entity = entityClass.newInstance();
			Iterator it = data.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        Field field = entity.getClass().getDeclaredField( (String) pairs.getKey() );
		        Boolean isAccessible = field.isAccessible();
		        field.setAccessible(true);
		        
		        if( pairs.getValue() != null && !pairs.getValue().equals(""))
		        	field.set(entity, ClassUtils.parseData(pairs.getValue(), field.getType()));
		        else 
		        	field.set(entity, null);
		        
		        field.setAccessible( isAccessible );
		    }
			
			return entity;
		} catch (SecurityException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (NoSuchFieldException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (IllegalArgumentException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (IllegalAccessException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (InstantiationException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (ParseException e) {
			Logger.getLogger(this.getClass()).error(e);
		}
		return null;
		
	}
	
	@SuppressWarnings("rawtypes")
	protected T populate(T entity, Map<?, ?> data){
		
		try {
			Iterator it = data.entrySet().iterator();
			while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        Field field = entity.getClass().getDeclaredField( (String) pairs.getKey() );
		        Boolean isAccessible = field.isAccessible();
		        field.setAccessible(true);
		        
		        if( pairs.getValue() != null && !pairs.getValue().equals(""))
		        	field.set(entity, ClassUtils.parseData(pairs.getValue(), field.getType()));
		        else 
		        	field.set(entity, null);
		        
		        field.setAccessible( isAccessible );
		    }
			
			return entity;
		} catch (SecurityException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (NoSuchFieldException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (IllegalArgumentException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (IllegalAccessException e) {
			Logger.getLogger(this.getClass()).error(e);
		} catch (ParseException e) {
			Logger.getLogger(this.getClass()).error(e);
		}
		return null;
		
	}
	
	public List<?> getObjectResponse(){
		return objectResponse;		
	}

}
