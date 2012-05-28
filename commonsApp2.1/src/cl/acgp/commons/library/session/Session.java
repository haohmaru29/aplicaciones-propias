package cl.acgp.commons.library.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Session {

	private String sessionId;
	private SessionManager sessionService;
	private Map<String, Object> sessionAttributes;
	private static final Map<Object, Session> instances = new HashMap<Object, Session>();
	
	private Session(String session){
		this.sessionId = session;
		sessionService = SessionManager.getInstance();
		sessionAttributes = new HashMap<String, Object>();
		unserializeObject();
	}
	
	public static Session initSession(String session){
		synchronized (instances) {
			if( !instances.containsKey( session ))
				instances.put(session, new Session( session ));
            
            return instances.get( session );
        }
	}
	
	public void setParameter(String key, Object value){
		sessionAttributes.put(key, value);
		updateData();
	}
	
	public Object getParameter(String key){
		unserializeObject();
		return sessionAttributes.get(key);
	}
	
	public String getSessionId(){
		return sessionId;
	}

	public void close(){
		sessionService.clearSession(sessionId);
	}
	
	private void updateData(){
		sessionService.updateSession(sessionId, serializeObject(), getDateTime());
	}
	
	private byte[] serializeObject(){
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(byteArray);
			oos.writeObject( sessionAttributes );
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch( NullPointerException ex){
		
		}
		
		return byteArray.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	private void unserializeObject(){
		Map<String, Object> session = sessionService.findSession(sessionId);
		
		byte[] buf = (byte[]) session.get("session_data");
		
	    try {
	    	if( buf != null ){
    			ObjectInputStream objectIn = new ObjectInputStream(new ByteArrayInputStream(buf));
    			sessionAttributes  =  (Map<String, Object>) objectIn.readObject();
	    		
	    	} else sessionService.registerSession(sessionId, serializeObject(), getDateTime(), SessionStatus.ACTIVE );
		    
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.ms");
        return dateFormat.format(new Date().getTime());
    }
}
