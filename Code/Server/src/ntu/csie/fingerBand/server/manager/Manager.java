package ntu.csie.fingerBand.server.manager;

import java.util.ArrayList;
import java.util.HashMap;


public class Manager {
	//session id and session manager pair
	
	private HashMap<Integer,SessionManager> allSessions = new HashMap<Integer, SessionManager>();
	private static Manager manager= new Manager();
	public static Manager getInstance(){
		return manager;
	}
	public SessionManager findSession(int id){
		return allSessions.get(id);
	}
	public SessionManager createSessionManager(String FB_name, String FB_id, int sessionID){
		SessionManager sm = new SessionManager(FB_name, FB_id,sessionID);
		System.out.println("session manager "+sm);
		allSessions.put(sessionID, sm);
		return sm;
	}
	public void endSessionManager(String sessionID){
		allSessions.get(sessionID).endSession();
		allSessions.remove(sessionID);
		
	}
	public SessionManager getSessionManager(int session_id){
		return allSessions.get(session_id);
	}
	public HashMap<Integer, SessionManager> getAllSessions(){
		return allSessions;
	}
}