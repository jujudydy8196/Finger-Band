package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.Application;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.invitation.InvitationManager;

public class InvitationDeletedHandler extends PacketHandler{

	public InvitationDeletedHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Delete_Outdated_Invitation") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		InvitationServiceHandler clientSH = (InvitationServiceHandler)serviceHandler;
		int sessionId = (Integer)packet.getItem("Session_id", Integer.class);
		Application.getInstance().getInvitationManager().deleteInvitation(sessionId);
		Packet returnPacket = new Packet("Delete_Outdated_Invitation_Response");
		returnPacket.addItems("Status", "OK");
		clientSH.sendCommand(returnPacket);
	}

}
