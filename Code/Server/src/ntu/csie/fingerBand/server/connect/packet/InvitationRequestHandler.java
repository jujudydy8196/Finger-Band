package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.Application;
import ntu.csie.fingerBand.server.invitation.*;

import java.util.ArrayList;

import ntu.csie.fingerBand.server.connect.InvitationServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;

public class InvitationRequestHandler extends PacketHandler{

	public InvitationRequestHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Request_Invitation") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		InvitationServiceHandler clientSH = (InvitationServiceHandler)serviceHandler;
		String fbid = (String)packet.getItem("FB_id", String.class);
		ArrayList<Invitation> list = Application.getInstance().getInvitationManager().getOwnInvitations(fbid);
		Packet returnPacket = new Packet("Request_Invitation_Response");
		//?
		returnPacket.addItems("Invitation_List", list);
		clientSH.sendCommand(returnPacket);

//		for (Invitation invitation: list) {
//			returnPacket.addItems("Session_id", invitation.getSessionId());
//			returnPacket.addItems("Session_Name", invitation.getSessionName());
//			returnPacket.addItems("Session_Initiator", invitation.getCreator());
//		}
		//return returnPacket;

		
	}

}
