package cl.tidev.commons.mvc.repository.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import cl.tidev.commons.helper.ClassUtils;
import cl.tidev.commons.helper.JpaUtils;
import cl.tidev.commons.mvc.domain.Domain;
import cl.tidev.commons.orm.jpa.JpaConnection;

public abstract class AbstractJpaController<T> {

    protected JpaConnection jpaConnection;
    protected Class<T> entityClass;

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @SuppressWarnings({"unchecked"})
    public AbstractJpaController() {
        try {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            entityClass = (Class<T>) thisType.getActualTypeArguments()[0];
            jpaConnection = JpaConnection.factory();
        } catch (SecurityException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }

    @SuppressWarnings({"unchecked"})
    public AbstractJpaController(String persistenceUnit) {
        try {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            entityClass = (Class<T>) thisType.getActualTypeArguments()[0];
            jpaConnection = JpaConnection.factory(persistenceUnit);
        } catch (SecurityException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }

    @SuppressWarnings({"unchecked"})
    public AbstractJpaController(JpaConnection connection) {
        try {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            entityClass = (Class<T>) thisType.getActualTypeArguments()[0];
            jpaConnection = connection;
        } catch (SecurityException e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }

    public AbstractJpaController(Class<T> clazz) {
        this.entityClass = clazz;
    }

    public void lazyInitialize(Object o) {
        Hibernate.initialize(o);
    }

    public T save(T entity) {
        if (entity == null) {
            throw new PersistenceException("Entity may not be null");
        }

        EntityManager em = null;
        jpaConnection.log("saving " + getEntityClass().getSimpleName() + " instance", Level.DEBUG, null);
        try {
            em = jpaConnection.getEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            if (em.contains(entity)) {
                em.merge(entity);
            } else {
                em.persist(entity);
            }

            em.getTransaction().commit();
            jpaConnection.log("save successful", Level.DEBUG, null);

        } catch (RuntimeException re) {
            if (jpaConnection.getEntityManager().isOpen()) {
                jpaConnection.rollback();
            }

            jpaConnection.log("save failed", Level.ERROR, re);
            throw re;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return entity;
    }

    public T update(Object id, Map<?, ?> params) {
        jpaConnection.log("updating " + getEntityClass().getSimpleName() + " instance", Level.DEBUG, null);

        T instance = null;
        EntityManager em = null;
        try {
            em = jpaConnection.getEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            instance = em.find(getEntityClass(), id);
            populate(instance, params);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            jpaConnection.log("update failed", Level.ERROR, e);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return instance;
    }

    @Deprecated
    public T update(T entity) {

        EntityManager em = null;
        if (entity == null) {
            throw new PersistenceException("Entity may not be null");
        }

        jpaConnection.log("updating " + getEntityClass().getSimpleName() + " instance", Level.DEBUG, null);
        try {
            em = jpaConnection.getEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            T result = jpaConnection.getEntityManager().merge(entity);

            em.getTransaction().commit();

            jpaConnection.log("update successful", Level.DEBUG, null);
            return result;
        } catch (RuntimeException re) {
            jpaConnection.log("update failed", Level.ERROR, re);
            throw re;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void delete(T entity) {
        EntityManager em = null;

        if (entity == null) {
            throw new PersistenceException("Entity may not be null");
        }

        jpaConnection.log("deleting " + getEntityClass().getSimpleName() + " instance", Level.DEBUG, null);
        try {
            em = jpaConnection.getEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.remove(entity);
            em.getTransaction().commit();
            jpaConnection.log("delete successful", Level.DEBUG, null);
            entity = null;
        } catch (RuntimeException re) {
            jpaConnection.log("delete failed", Level.ERROR, re);
            throw re;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deleteById(Object id) {
        EntityManager em = null;
        jpaConnection.log("deleting instance", Level.DEBUG, null);
        try {
            em = jpaConnection.getEntityManager();
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            T entity = findById(id);
            if (entity == null) {
                throw new PersistenceException("Entity may not be null");
            }
            em.remove(entity);
            em.getTransaction().commit();
            jpaConnection.log("delete successful", Level.DEBUG, null);
            entity = null;
        } catch (RuntimeException re) {
            jpaConnection.log("delete failed", Level.ERROR, re);
            throw re;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public Class<?> getIdFieldType() {
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

    public String getIdField() {
        String idField = null;
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (JpaUtils.isIdAnnotation(field)) {
                idField = field.getName();
                break;
            }
        }

        return idField;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(final int... rowStartIdxAndCount) {
        jpaConnection.log("finding all instances", Level.DEBUG,
                null);
        try {
            EntityManager em = jpaConnection.getEntityManager();
            final String queryString = "select model from " + getEntityClass().getSimpleName() + " model";
            Query q = em.createQuery(queryString);
            if (rowStartIdxAndCount != null && rowStartIdxAndCount.length > 0) {
                int rowStartIdx = Math.max(0, rowStartIdxAndCount[0]);
                if (rowStartIdx > 0) {
                    q.setFirstResult(rowStartIdx);
                }

                if (rowStartIdxAndCount.length > 1) {
                    int rowCount = Math.max(0, rowStartIdxAndCount[1]);
                    if (rowCount > 0) {
                        q.setMaxResults(rowCount);
                    }
                }
            }

            List<?> results = q.getResultList();


            return (List<T>) results;
        } catch (RuntimeException re) {
            jpaConnection.log("find all failed", Level.ERROR, re);
            throw re;
        }

    }

    public List<Map<?, ?>> findAll() {
        List<T> listObjects = findAll(0);
        List<Map<?, ?>> listMap = new ArrayList<Map<?, ?>>();

        ListIterator<T> iterator = listObjects.listIterator();

        while (iterator.hasNext()) {
            listMap.add(Domain.getMap(iterator.next()));
        }

        return listMap;
    }

    public T findById(Object id) {
        if (id == null) {
            throw new PersistenceException("Id may not be null or negative");
        }

        T instance = null;
        jpaConnection.log("finding instance with id: " + id,
                Level.DEBUG, null);
        try {
            EntityManager em = jpaConnection.getEntityManager();
            instance = em.find(getEntityClass(), id);

        } catch (RuntimeException e) {
            jpaConnection.log("find failed", Level.ERROR, e);

        }

        return instance;
    }

    public T getEntityReference(Object id) {
        T entity = jpaConnection.getEntityManager().getReference(getEntityClass(), id);
        jpaConnection.getEntityManager().refresh(entity);
        Hibernate.initialize(entity);

        return entity;
    }

    @SuppressWarnings("rawtypes")
    public List<T> findByAttributes(Map<String, Object> attributes) {
        List<T> results;
        //set up the Criteria query
        EntityManager em = jpaConnection.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
        Root<T> foo = cq.from(getEntityClass());

        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String s : attributes.keySet()) {
            if (foo.get(s) != null) {
                cb.equal((Expression) foo.get(s), attributes.get(s));
                //predicates.add(cb.like((Expression) foo.get(s), "%" + attributes.get(s) + "%" ));
            }
        }

        cq.where(predicates.toArray(new Predicate[]{}));
        results = em.createQuery(cq).getResultList();


        return results;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> findByProperty(String propertyName,
            final Object value, final int... rowStartIdxAndCount) {
        jpaConnection.log("finding instance with property: "
                + propertyName + ", value: " + value, Level.DEBUG, null);
        try {
            EntityManager em = jpaConnection.getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
            Root<T> foo = cq.from(getEntityClass());

            List<Predicate> predicates = new ArrayList<Predicate>();

            if (value.getClass().equals(String.class)) {
                predicates.add(cb.like((Expression) foo.get(propertyName), value + "%"));
            } else {
                predicates.add(cb.equal((Expression) foo.get(propertyName), value));
            }

            cq.where(predicates.toArray(new Predicate[]{}));
            List<?> results = (List<?>) ((ArrayList) em.createQuery(cq).getResultList()).clone();

            return (List<T>) results;
        } catch (RuntimeException re) {
            jpaConnection.log("find by property name failed",
                    Level.ERROR, re);
            throw re;
        }
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
}
