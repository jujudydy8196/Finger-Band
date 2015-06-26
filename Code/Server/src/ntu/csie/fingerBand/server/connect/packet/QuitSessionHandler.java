package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.manager.Manager;
import ntu.csie.fingerBand.server.manager.SessionManager;

public class QuitSessionHandler extends PacketHandler{

	public QuitSessionHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		// TODO Auto-generated method stub 
		return packet.getCommand().equals("Session_Quit");
	}

	@Override
	protected void handle(Packet packet) {
		int session_id = (Integer) packet.getItem("Session_id", java.lang.Integer.class);
		String FB_id = (String) packet.getItem("FB_id", java.lang.String.class);
		SessionManager sessionManager = Manager.getInstance().getSessionManager(session_id);
		sessionManager.getTrack(FB_id).cutBind();
		sessionManager.deleteTrack(FB_id);
		
	}

}
