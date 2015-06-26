package ntu.csie.fingerBand.server.connect.packet;

import java.sql.SQLException;

import com.google.gson.Gson;

import ntu.cise.fingerBand.DB.DbConnector;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.databean.Music;
import ntu.csie.fingerBand.server.manager.Manager;
import ntu.csie.fingerBand.server.manager.SessionManager;

public class EndSessionHandler extends PacketHandler {

	public EndSessionHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().equals("Session_Over");
	}

	@Override
	protected void handle(Packet packet) {
		int session_id = (Integer) packet.getItem("Session_id", java.lang.Integer.class);
	//	String FB_id = (String) packet.getItem("FB_id", java.lang.String.class);
		SessionManager sessionManager = Manager.getInstance().getSessionManager(session_id);
		
		
		
		sessionManager.endSession();
		
	}
	


}
