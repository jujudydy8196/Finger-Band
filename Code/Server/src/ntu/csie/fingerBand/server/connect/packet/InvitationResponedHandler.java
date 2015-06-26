package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.Application;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.invitation.Invitation;
import ntu.csie.fingerBand.server.invitation.InvitationManager;

public class InvitationResponedHandler extends PacketHandler {

	public InvitationResponedHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Join_Session") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		InvitationServiceHandler clientSH = (InvitationServiceHandler)serviceHandler;
		String fbid = (String)packet.getItem("FB_id", String.class);
		int sessionId = (Integer)packet.getItem("Session_id", Integer.class);
//		Invitation invitation = (Invitation)packet.getItem("invitation", Invitation.class);
		Application.getInstance().getInvitationManager().respondInvitation(fbid,sessionId);
		Packet returnPacket = new Packet("Join_Session_Response");
		returnPacket.addItems("Status", "OK");
		clientSH.sendCommand(returnPacket);

		//return returnPacket;
	}

}
