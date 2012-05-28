package cl.acgp.commons.library.session;

import java.util.Map;

import cl.acgp.commons.app.service.EjeWebSessionManager;
import cl.acgp.commons.helper.ClassUtils;


public class SessionManager {
	
	private static SessionManager instance;
	private EjeWebSessionManager sessionManager;
	
	public static SessionManager getInstance(){
		if( instance == null){
			instance = new SessionManager();
			instance.sessionManager = new EjeWebSessionManager();
		}
		
		return instance;
	}
	
	private SessionManager(){
		
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> findSession(String sessionId){
		return ClassUtils.getMap(sessionManager.findById(sessionId));
	}
	
	public void registerSession(String sessionId, byte[] data, String date, SessionStatus status){
		//sessionManager.registerSession(sessionId, data, date, status);
	}
	
	public void updateSession(String sessionId, byte[] data, String date){
		//sessionManager.updateSession(sessionId, data, date);
	}
	
	public void clearSession(String sessionId){
		sessionManager.deleteById(sessionId);
	}
	
}
