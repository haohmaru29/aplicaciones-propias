package cl.tidev.commons.orm;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import cl.tidev.commons.helper.ClassUtils;
import cl.tidev.commons.helper.PropertiesUtils;

public class ConnectionManager {
	private PoolingDriver poolingDriver = new PoolingDriver();
	
	final Boolean defaultReadOnly = false;
    final Boolean defaultAutoCommit = true;
    
	private static ConnectionManager instance = new ConnectionManager();
	
	
	private ConnectionManager(){
		Properties dbprops = PropertiesUtils.loadProperties("db.properties");
		List<String> poolKeys = new ArrayList<String>();
		Enumeration<?> propNames = dbprops.propertyNames();
		
	 
		while( propNames.hasMoreElements() ){
			String name = (String) propNames.nextElement();
			
			String poolName = name.substring(0, name.lastIndexOf("."));
			if( !poolKeys.contains(poolName)){
				poolKeys.add( poolName );
				createPool( poolName, dbprops);
			}
		}
			
	}
	
	public static ConnectionManager getInstance(){
		return instance;
	}
	
	public synchronized Connection getConnection(String poolName){
		Connection conn = null;
		try {
			GenericObjectPool pool = (GenericObjectPool) poolingDriver.getConnectionPool( poolName );
			
			if( pool.getNumActive() == pool.getMaxIdle() ) {
				if( pool.getNumActive() < pool.getMaxActive() ){
					pool.setMaxIdle( pool.getMaxIdle() + 1  ); pool.addObject(); 
				} else {
					throw new Exception("Maximum number of connections exceeded");
				}
			}
			
			conn = DriverManager.getConnection( ("jdbc:apache:commons:dbcp:").concat( poolName ) );
			
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException: " + ex.getMessage() ); 
		} catch (Exception ex) {
			Logger.getLogger( this.getClass() ).error( "PortalException: " + ex.getMessage() );
		} 
		
		return conn;
	}
	
	public void getStatus(String poolName){
		GenericObjectPool pool;
		try {
			pool = (GenericObjectPool) poolingDriver.getConnectionPool( poolName );
			Logger.getLogger( this.getClass() ).info(	StringUtils.capitalize(poolName) + " Connection => Active: " + pool.getNumActive() + ", Idle: " + pool.getNumIdle() + ", maxIdle: " + pool.getMaxIdle() + ", maxActive: " + pool.getMaxActive());
			
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [getStatus]: " + ex.getMessage() );
		} 
		
	}
	
	public void freeConnection(String poolName, Connection conn) throws SQLException, Exception{
		poolingDriver.getConnectionPool( poolName ).returnObject(conn);
		poolingDriver.getConnectionPool( poolName ).borrowObject();
	}
	
	public GenericObjectPool getPool(String poolName) throws SQLException{
		return (GenericObjectPool) poolingDriver.getConnectionPool( poolName );
	}
	
	private void createPool(String poolName, Properties properties){
		GenericObjectPool pool = new GenericObjectPool();
		Integer maxIdle = Integer.parseInt(properties.getProperty(String.valueOf(poolName).concat(".maxIdle"))); 
	    Integer maxActive = Integer.parseInt(properties.getProperty(String.valueOf(poolName).concat(".maxActive")));
	    
		pool.setTestOnReturn(true);
		pool.setTestOnBorrow(true);
		pool.setMaxIdle( maxIdle );
		pool.setMaxActive(maxActive);
		
		Properties props = new Properties();
	    props.setProperty("user", properties.getProperty(String.valueOf(poolName).concat(".user")));
	    props.setProperty("Password", properties.getProperty(String.valueOf(poolName).concat(".password")));
	    
		ConnectionFactory cf = null;
		try {
			cf = new DriverConnectionFactory( 
						(Driver) ClassUtils.getInstance( properties.getProperty(String.valueOf(poolName).concat(".driver")) )
						, properties.getProperty(String.valueOf(poolName).concat(".url"))
						, props);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    new PoolableConnectionFactory(cf, pool, new GenericKeyedObjectPoolFactory(null),
	    		properties.getProperty(String.valueOf(poolName).concat(".validationQuery")),
                    defaultReadOnly, defaultAutoCommit);

	    poolingDriver.registerPool(poolName, pool);
	    
		try {
			for(int i = 0 ; i < maxIdle ; i++) pool.addObject();
		} catch (Exception ex) {
			Logger.getLogger( this.getClass() ).error( "Exception [createPool]: " + ex.getMessage() );
		}
	    
	}
	
}
