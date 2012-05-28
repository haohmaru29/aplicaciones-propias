package cl.acgp.commons.orm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;


public class ResultManager {
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private CallableStatement callableStatement;
	private Statement statement;
	private String poolName;
	private ConnectionManager connectionManager;
	private Connection connection;
	private List<Map<String, Object>> resultList;
	private String errorMessage;
	private Integer rowsAffected;
	
	public ResultManager(){ 
		poolName = "default";
		resultList = new ArrayList<Map<String, Object>>();
	}
	
	public ResultManager(String pool){
		poolName = pool;
		resultList = new ArrayList<Map<String, Object>>();
	}
	
	private synchronized void initConnection(){
		connectionManager = ConnectionManager.getInstance();
		connection = connectionManager.getConnection(poolName);
		connectionManager.getStatus(poolName);
		
	}
	
	public void execute(String query){
		try {
			initConnection();
			preparedStatement = connection.prepareStatement(query);
			
			Logger.getLogger( this.getClass() ).info( "Query [execute]: ".concat(query) );
			resultSet = preparedStatement.executeQuery();
			
			buildMap();
			
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [execute]: " + ex.getMessage() + ", query: " + query );
		} catch (Exception ex) {
			Logger.getLogger( this.getClass() ).error( "Exception [execute]: " + ex.getMessage() + ", query: " + query );
		} finally {
			release();
		}
	}
	
	public void executeCall(String query){
		try {
			initConnection();
			callableStatement = connection.prepareCall(String.valueOf(new StringBuffer("{call ").append( query ).append("}")));
			
			Logger.getLogger( this.getClass() ).info( "Query [executeCall]: ".concat(query) );
			resultSet = callableStatement.executeQuery();
			
			buildMap();
			
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [executeCall]: " + ex.getMessage()  + ", query: " + query);
		} catch (Exception ex) {
			Logger.getLogger( this.getClass() ).error( "Exception [executeCall]: " + ex.getMessage() + ", query: " + query);
		} finally {
			release();
		}
	}
	
	private void release(){
		try {
			
			if( resultSet != null) resultSet.close(); 
			if( callableStatement != null) callableStatement.close();
			if( preparedStatement != null) preparedStatement.close();
			if( statement != null) statement.close();
			
			connection.close();
			connectionManager.getStatus(poolName);
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [release]: " + ex.getMessage() );
		} catch (Exception ex) {
			Logger.getLogger( this.getClass() ).error( "Exception [release]: " + ex.getMessage() );
		} 
	}
	
	private Boolean exec(String query, String method) {
		Boolean success = false;
		Logger.getLogger( this.getClass() ).debug( query );
		try {
			initConnection();
			statement = connection.createStatement();
			
			Logger.getLogger( this.getClass() ).info( "Query [exec - "+ method +"]: ".concat(query) );
			
			rowsAffected = statement.executeUpdate(query);
			if (rowsAffected > 0) success = true;
			else errorMessage = "[" + method + "]: filas afectadas => " + rowsAffected.toString();
			
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [exec]: " + ex.getMessage() + ", query: " + query );
		} catch (Exception ex) {
			Logger.getLogger( this.getClass() ).error( "Exception [exec]: " + ex.getMessage() + ", query: " + query );
		} finally{
			release();
		}
		
		return success;
	}
	
	public Boolean execInsert(String query){
		return exec(query, "execInsert");
	}
	
	public Boolean execDelete(String query) {   
		return exec(query, "execDelete");
	}
	
	public Boolean execUpdate(String query) {   
		return exec(query, "execUpdate");
    }
	
	private void buildMap(){
		ResultSetMetaData rsMetaData;
		
		try {
			if( !resultList.isEmpty()) resultList.clear();
			rsMetaData = resultSet.getMetaData();
			while(resultSet.next()){
				Map<String, Object> o = new HashMap<String, Object>();
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
		    		String columnName = rsMetaData.getColumnName(i);
		    		o.put(columnName, resultSet.getString( columnName ));
		    	}
				resultList.add(o);
			}
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [buildMap]: " + ex.getMessage() );
		} 
	}
	
	public List<?> getList(){
		return ((List<?>) ((ArrayList<?>) resultList).clone());
	}

	public Object getParameter(String columnName){
		return ((Map<?, ?>) resultList.get(0)).get(columnName);
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger( this.getClass() ).error( "SQLException [closeConnection]: " + ex.getMessage() );
		}
	}
	
}
