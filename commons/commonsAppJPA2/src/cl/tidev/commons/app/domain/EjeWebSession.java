package cl.tidev.commons.app.domain;

import cl.tidev.commons.mvc.domain.Domain;

// Generated 27-09-2010 10:12:43 AM by Hibernate Tools 3.3.0.GA

/**
 * EjeWebSession generated by hbm2java
 */
@SuppressWarnings("serial")
public class EjeWebSession extends Domain implements java.io.Serializable {

	private String sessionId;
	private String sessionCreate;
	private byte[] sessionData;
	private String sessionModified;
	private Integer sessionStatus;

	public EjeWebSession() {
	}

	public EjeWebSession(String sessionId) {
		this.sessionId = sessionId;
	}

	public EjeWebSession(String sessionId, String sessionCreate,
			byte[] sessionData, String sessionModified, Integer sessionStatus) {
		this.sessionId = sessionId;
		this.sessionCreate = sessionCreate;
		this.sessionData = sessionData;
		this.sessionModified = sessionModified;
		this.sessionStatus = sessionStatus;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionCreate() {
		return this.sessionCreate;
	}

	public void setSessionCreate(String sessionCreate) {
		this.sessionCreate = sessionCreate;
	}

	public byte[] getSessionData() {
		return this.sessionData;
	}

	public void setSessionData(byte[] sessionData) {
		this.sessionData = sessionData;
	}

	public String getSessionModified() {
		return this.sessionModified;
	}

	public void setSessionModified(String sessionModified) {
		this.sessionModified = sessionModified;
	}

	public Integer getSessionStatus() {
		return this.sessionStatus;
	}

	public void setSessionStatus(Integer sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

}
