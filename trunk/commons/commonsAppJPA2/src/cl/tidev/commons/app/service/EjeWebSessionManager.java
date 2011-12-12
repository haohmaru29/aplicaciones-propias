package cl.tidev.commons.app.service;

import cl.tidev.commons.app.domain.EjeWebSession;
import cl.tidev.commons.mvc.service.jpa.AbstractServiceManager;

public class EjeWebSessionManager extends AbstractServiceManager<EjeWebSession> {

	/*public Map<String, Object> findSession(String sessionId){
		return ClassUtils.getMap(jpaDao.findById(sessionId));
	}
	
	public void registerSession(String sessionId, byte[] data, String date, SessionStatus status){
		EjeWebSession ejeWebSession = new EjeWebSession();
		ejeWebSession.setSessionId(sessionId);
		ejeWebSession.setSessionStatus(status.ordinal());
		ejeWebSession.setSessionCreate(date);
		ejeWebSession.setSessionData(data);
		
		jpaDao.save(ejeWebSession);
	}
	
	public void updateSession(String sessionId, byte[] data, String date){
		EjeWebSession ejeWebSession = jpaDao.findById(sessionId);
		ejeWebSession.setSessionModified(date);
		ejeWebSession.setSessionData(data);
		
		jpaDao.update(ejeWebSession);
	}*/
	
}
