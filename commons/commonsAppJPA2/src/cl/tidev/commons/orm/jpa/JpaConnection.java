package cl.tidev.commons.orm.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import cl.tidev.commons.helper.PropertiesUtils;

public class JpaConnection {

    private static Map<String, JpaConnection> instances = new HashMap<String, JpaConnection>();
    private static ResourceBundle resource = PropertiesUtils.getResource("module");
    @PersistenceContext
    private EntityManagerFactory emf;
    private final ThreadLocal<EntityManager> threadLocal;
    private final Logger logger;

    private JpaConnection(String persistenceUnit) {
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
        threadLocal = new ThreadLocal<EntityManager>();
        logger = Logger.getLogger(getClass());
        logger.setLevel(Level.WARN);
    }

    public static JpaConnection factory() {
        String persistenceUnit = resource.getString("module.jpa.pu").trim();

        if (!instances.containsKey(persistenceUnit)) {
            instances.put(persistenceUnit, new JpaConnection(persistenceUnit));
        }

        return instances.get(persistenceUnit);
    }

    public static JpaConnection factory(String persistenceUnit) {
        if (!instances.containsKey(persistenceUnit)) {
            instances.put(persistenceUnit, new JpaConnection(persistenceUnit));
        }

        return instances.get(persistenceUnit);
    }

    public EntityManager getEntityManager() {
        EntityManager manager = threadLocal.get();

        if (manager == null || !manager.isOpen()) {
            manager = emf.createEntityManager();
            threadLocal.set(manager);
        }
        return manager;
    }

    public void closeEntityManager() {
        EntityManager em = threadLocal.get();
        threadLocal.set(null);
        if (em != null) {
            em.close();
        }
    }

    public void beginTransaction() {
        getEntityManager().getTransaction().begin();
    }

    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    public void rollback() {
        getEntityManager().getTransaction().rollback();
    }

    public Query createQuery(String query) {
        return getEntityManager().createQuery(query);
    }

    public void log(String info, Level level, Exception ex) {
        logger.log(level, info, ex);
    }

    public EntityManagerFactory getEMFactory() {
        return emf;
    }
}
