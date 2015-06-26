package ntu.csie.fingerBand.server.connect.packet;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Calendar;

import ntu.cise.fingerBand.DB.DbConnector;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.manager.Manager;
import ntu.csie.fingerBand.server.manager.SessionManager;

public class JoinSessionHandler extends PacketHandler{
	private static final int SESSION_NOT_EXIST = -1;

	public JoinSessionHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
		// TODO Auto-generated constructor stub
	}
	protected boolean isResponsible(Packet packet){
		if(packet.getCommand().equals("Join_Session"))
			return true;
		return false;
	}
	protected void handle(Packet packet){
		
		String name = (String) packet.getItem("Session_Name", java.lang.String.class);
		String FB_id = (String) packet.getItem("FB_id", java.lang.String.class);
		String FB_name=(String) packet.getItem("FB_name", java.lang.String.class);
		int session_id = (Integer) packet.getItem("Session_id", java.lang.Integer.class);
		DbConnector dbConn = new DbConnector();
		try {
			dbConn.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//dbConn.getFbIdByFbName(String FbId);
		//if(Manager.getInstance().sessionExist(dbConn.))
		//CREATE
				Manager manager = Manager.getInstance();
				SessionManager sessionManager;
				if(session_id == SESSION_NOT_EXIST){//create a session
					session_id = dbConn.createSession(FB_id, name);
					sessionManager = manager.createSessionManager(FB_name, FB_id, session_id);
//					Calendar cal = Calendar.getInstance();
					sessionManager.setStartDate(new Date());
					
					
				}
				else{//Just join session
//					Iterator it = manager.getAllSessions().entrySet().iterator();
//					while(it.hasNext()){
//						Map.Entry pair = (Entry) it.next();
//							System.out.println("ID: "+pair.getKey()+" valse "+pair.getValue());
//					}
					sessionManager = manager.getSessionManager(session_id);
				}
					
			
					PacketHandler packetHandler=serviceHandler.getPacketHandler();
					while(packetHandler  != null){
						if(packetHandler instanceof TransmitHandler){
							sessionManager.addTrack(FB_id, (TransmitHandler)packetHandler);
							((TransmitHandler) packetHandler).setSessionManager(sessionManager);
							((TransmitHandler) packetHandler).setTrackRecorder(sessionManager.getTrackRecorder(FB_id));
							break;
						}
						packetHandler = packetHandler.getNextPacketHandler();
					}
					dbConn.addSessionMember(session_id, FB_id);
					
					
					Packet pck = new Packet("Session_id_Response");
					pck.addItems("Session_id", session_id);
					serviceHandler.sendCommand(pck);
					
		
	}

}
