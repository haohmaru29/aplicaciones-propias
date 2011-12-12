package cl.tidev.commons.orm.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import cl.tidev.commons.helper.PropertiesUtils;

public abstract class AbstractJpaConnection {
	protected static Map<String, JpaConnection> instances = new HashMap<String, JpaConnection>();
	protected static ResourceBundle resource = PropertiesUtils.getResource("module");
	
	protected EntityManagerFactory emf;
	protected final ThreadLocal<EntityManager> threadLocal;
	protected final Logger logger;
	
	protected AbstractJpaConnection(String persistenceUnit){ 
		threadLocal = new ThreadLocal<EntityManager>();
		logger = Logger.getLogger( getClass() );
		logger.setLevel( Level.WARN );
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
        if (em != null) em.close();
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
	
	public EntityManagerFactory getEMFactory(){
		return emf;
	}
}
