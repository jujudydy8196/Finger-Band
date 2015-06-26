package fingerBand.invite;

public class Invitation {
	private String creatorID;
	private String creatorName;
	private int sessionID;
	private String sessionName;
	
	public String getCreatorID() {
		return creatorID;
	}
	
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	public int getSessionID() {
		return sessionID;
	}
	
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	
	public String getSessionName() {
		return sessionName;
	}
	
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

}
