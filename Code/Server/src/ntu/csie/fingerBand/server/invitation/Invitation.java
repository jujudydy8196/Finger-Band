package ntu.csie.fingerBand.server.invitation;


public  class Invitation {
	public String creatorID;
	public int sessionID;
	public String sessionName;
	public String creatorName;
	public Invitation(String fbid, String name, int sessionid, String creatorname){
		creatorID = fbid;
		sessionID = sessionid;
		sessionName = name;
		creatorName = creatorname;
	}
	public int getSessionId() {
		return sessionID;
	}
	
	public String getCreator() {
		return creatorID;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	public String getSessionName() {
		return sessionName;
	}
	
}