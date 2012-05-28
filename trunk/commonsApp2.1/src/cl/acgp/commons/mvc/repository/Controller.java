package cl.acgp.commons.mvc.repository;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.acgp.commons.helper.ArrayUtils;
import cl.acgp.commons.orm.ResultManager;
import cl.acgp.commons.orm.annotations.Column;
import cl.acgp.commons.orm.annotations.Columns;
import cl.acgp.commons.orm.annotations.Table;
import cl.acgp.commons.orm.data.DataType;

public abstract class Controller {

	protected String query;
	protected String message;
	protected Boolean success;
	protected Integer state = -1; 
	protected ResultManager resultManager;
	
	protected static Map<String, String> namedQuery = new HashMap<String, String>();
	protected static Map<String, String> namedProcedure = new HashMap<String, String>();
	
	static{
		namedQuery.put("select", "SELECT * FROM :table");
		namedQuery.put("selectById", "SELECT * FROM :table WHERE :pk = ?");
		namedQuery.put("selectByFields", "SELECT * FROM :table WHERE :keys");
		
		namedQuery.put("insert", "INSERT INTO :table (:fields) VALUES(:values)");
		namedQuery.put("update", "UPDATE :table SET :set WHERE :pk = ?");
		
		namedQuery.put("deleteById", "DELETE FROM :table WHERE :pk = ?");
		namedQuery.put("deleteByFields", "DELETE FROM :table WHERE :keys");
		
		namedQuery.put("exist", "SELECT COUNT(*) AS exist FROM :table WHERE :pk = ?");
	}
	
	public Controller(){
		resultManager = new ResultManager();
	}
	
	public Controller(String poolName){
		resultManager = new ResultManager(poolName);
	}
	
	public Boolean getSuccess(){ return this.success; }
	
	public Integer getState(){ return this.state; }
	
	public String getMessage() {
		if( success ) message = "Operacion Realizada Correctamente";
		else message = resultManager.getErrorMessage();
		
		return message;
	}
	
	protected void appendValues(Map<?,?> params){
		StringBuffer fieldsBuffer = new StringBuffer();
		StringBuffer valuesBuffer = new StringBuffer();
		Boolean first = true;
		Iterator<?> it = params.entrySet().iterator();
		
	    while (it.hasNext()) {
	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	        if( !pairs.getKey().equals("m") && !pairs.getKey().equals("c")){
	        	if (!first) { fieldsBuffer.append(", "); valuesBuffer.append(", "); }
	        	fieldsBuffer.append( pairs.getKey().toString() );
	        	valuesBuffer.append( parse(pairs.getKey().toString(), ArrayUtils.extract( (String[]) params.get(pairs.getKey().toString()) )).toString()  );
	        	first = false;
	        }
	        
	    }
	    
	    query = query.replace(":fields", fieldsBuffer.toString() );
	    query = query.replace(":values", valuesBuffer.toString() );
	    
	}
	
	protected void appendSet(Map<?, ?> params){
		StringBuffer queryBuffer = new StringBuffer();
		Boolean first = true;
		Iterator<?> it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	        
	        if( pairs.getKey().equals( getPK() ) ){
	        	query = query.replace("?", parse(ArrayUtils.extract( (String[]) params.get(pairs.getKey().toString()) )).toString()  );
	        }
	        
	        if( !pairs.getKey().equals( getPK() )){
	        	if( !first ) queryBuffer.append(", ");
	        	queryBuffer.append( pairs.getKey().toString() )
	        		.append(" = ")
	        		.append( parse(pairs.getKey().toString(), ArrayUtils.extract( (String[]) params.get(pairs.getKey().toString()) )).toString()  );
	        	first = false;
	        }
	    }
	    query = query.replace(":set", queryBuffer.toString() );
	    
	}
	
	protected void appendWhere(Map<?, ?> params){
		StringBuffer queryBuffer = new StringBuffer();
		Boolean first = true;
		Iterator<?> it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	        
	        if( !pairs.getKey().equals("m") && !pairs.getKey().equals("c")){
	        	if( !first ) queryBuffer.append(" AND ");
	        	queryBuffer.append( pairs.getKey().toString() )
	        		.append(" = ")
	        		.append( parse(pairs.getKey().toString(), ArrayUtils.extract( (String[]) params.get(pairs.getKey().toString()) )).toString()  );
	        	first = false;
	        }
	    }
	    query = query.replace(":keys", queryBuffer.toString() );
	    
	}
	
	protected void bindParameter(Object param){
		query = query.replace("?", param.toString() );
	}
	
	protected void bindParameter(String key, Object param){
		query = query.replace(key, param.toString() );
	}
	
	protected void bindFields(Map<?,?> params){
		Iterator<?> it = params.entrySet().iterator();
	    
		while (it.hasNext()) {
	        Map.Entry<?,?> pairs = (Map.Entry<?,?>)it.next();
	        query = query.replace( ":" + pairs.getKey().toString() , parse(pairs.getKey().toString(), ArrayUtils.extract( (String[]) params.get(pairs.getKey().toString()) )).toString()  );
	    }
	}
	
	private Object parse(String column, String value){
		String parsedValue = null;
		
		switch( getColumnType(column) ){
			case UNDEFINED:
				parsedValue = parse(value).toString();
				break;
				
			case STRING:
			case DATE:
				parsedValue = new StringBuffer().append("'").append(value).append("'").toString();
				break;
				
			case NUMERIC: 
			case BOOLEAN:
			case SQL:
				parsedValue = value.toString();
				break;
		}
		
		return parsedValue;
		
	}
	
	private Object parse(String str){
		try {
			Integer.parseInt(str);
			return str;
			
		} catch (NumberFormatException nfe){
			if( str.equals("getDate()")) return str;
			else return new StringBuffer().append("'").append(str).append("'").toString();
		}
	}
	
	public List<?> executeNamedQuery( String key, Map<?,?> params){
		query = namedQuery.get(key);
		bindFields(params);

		resultManager.execute(query);
		return resultManager.getList();
	}

	public List<?> executeNamedQuery( String key, Object param){
		query = namedQuery.get(key);
		bindParameter( parse(param.toString()) );

		resultManager.execute(query);
		return resultManager.getList();
	}
	
	public List<?> executeNamedQuery( String key){
		query = namedQuery.get(key);
		resultManager.execute(query);

		return resultManager.getList();
	}
	
	public void insert(Map<?,?> params){
		query = namedQuery.get("insert");
		
		if( params.containsKey("c"))
			params.remove("c");
		
		if( params.containsKey("m"))
			params.remove("m");
		
		bindParameter(":table", getTable());
		appendValues(params);

		success = resultManager.execInsert(query);
	}
	
	public void update(Map<?,?> params){
		query = namedQuery.get("update");
		
		if( params.containsKey("c"))
			params.remove("c");
		
		if( params.containsKey("m"))
			params.remove("m");
		
		bindParameter(":table", getTable());
		bindParameter(":pk", getPK());
		
		appendSet( params );
		
		success = resultManager.execUpdate(query);
	}
	
	public void insertOrUpdate(Map<?,?> params){
		if( exist( ArrayUtils.extract((String[]) params.get( getPK() ))  ) > 0 ){
			update(params);
		} else {
			insert(params);
		}
	}
	
	public void delete(Object id){
		query = namedQuery.get("deleteById");
		
		bindParameter(":table", getTable());
		bindParameter(":pk", getPK());
		bindParameter( parse( getPK(), id.toString()) );
		
		success = resultManager.execDelete(query);
	}
	
	public void delete(Map<?,?> params){
		query = namedQuery.get("deleteByFields");
		
		bindParameter(":table", getTable());
		appendWhere(params);
		
		success = resultManager.execDelete(query);
	}
	
	public List<?> select(Map<?,?> params){
		query = namedQuery.get("selectByFields");
		
		bindParameter(":table", getTable());
		appendWhere(params);
		
		resultManager.execute(query);
		return resultManager.getList();
	}
	
	public Integer exist(Object id){
		query = namedQuery.get("exist");
		
		bindParameter(":table", getTable() );
		bindParameter(":pk", getPK());
		bindParameter( parse( getPK(), id.toString()) );
		
		resultManager.execute(query);
		System.out.println( query );
		System.out.println( resultManager.getParameter("exist").toString() );
		return 0;
	}
	
	public List<?> select(Object id){
		query = namedQuery.get("selectById");
		
		bindParameter(":table", getTable() );
		bindParameter(":pk", getPK());
		bindParameter( parse( getPK(), id.toString()) );
		
		resultManager.execute(query);
		return resultManager.getList();
	}
	
	public List<?> select(){
		query = namedQuery.get("select");
		
		bindParameter(":table", getTable() );
		
		resultManager.execute(query);
		return resultManager.getList();
	}
	
	public List<?> call(String key, Map<?, ?> params){
		query = namedProcedure.get( key );
		
		bindFields(params);
		resultManager.executeCall( query );
		return resultManager.getList();
	}
	
	public List<?> call(String key){
		query = namedProcedure.get( key );
		
		resultManager.executeCall( query );
		return resultManager.getList();
	}
	
	private Annotation getTableAnnotation(){
		Annotation[] annotations = this.getClass().getAnnotations();
		
		for( int i = 0; i< annotations.length ; i++){
			if( Table.class.isAssignableFrom(annotations[i].getClass()) )
				return annotations[i];
		}
		
		return null;
	}
	
	private Annotation getColumnsAnnotation(){
		Annotation[] annotations = this.getClass().getAnnotations();
		
		for( int i = 0; i< annotations.length ; i++){
			if( Columns.class.isAssignableFrom(annotations[i].getClass()) )
				return annotations[i];
		}
		
		return null;
	}
	
	private String getTable(){
		try{
			Annotation annotation = getTableAnnotation();
			return ((Table) annotation).name();
		} catch(Exception ex){
			Logger.getLogger( this.getClass() ).error( "Exception [getTable]: " + ex.getMessage() );
		}
		
		
		return null;
	}
	
	private String getPK(){
		Table annotation = (Table) getTableAnnotation();
		return annotation.primaryKey();
	}
	
	private DataType getColumnType(String column){
		try{
			Columns annotation = (Columns) getColumnsAnnotation();
			Integer count;
			
			Column[] c = ((Columns) annotation).value();
			
			for( count = 0; count < c.length ; count++){
				if( column.equals( ((Column) c[count]).name() ) ){
					return ((Column) c[count]).type();
				}
			}
		} catch (Exception ex){
			Logger.getLogger( this.getClass() ).error( "Exception [getColumnType]: " + ex.getMessage() );
		}
		
		
		return DataType.UNDEFINED;
	}
	
	public Object getParameter(String columnName){
		return resultManager.getParameter(columnName);
	}
}
