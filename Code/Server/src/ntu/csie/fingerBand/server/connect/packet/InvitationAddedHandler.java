package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.connect.LoginServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

import ntu.csie.fingerBand.server.Application;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.invitation.*;
import ntu.csie.fingerBand.server.manager.Manager;

public class InvitationAddedHandler extends PacketHandler{

	public InvitationAddedHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Add_Invitation") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		InvitationServiceHandler clientSH = (InvitationServiceHandler)serviceHandler;
//		Invitation invitation;
		int sessionId = (Integer)packet.getItem("Session_id", Integer.class);
		String sessionName = (String)packet.getItem("Session_Name",String.class);
		String Fbid = (String)packet.getItem("FB_id", String.class);

		Manager.getInstance().createSessionManager("uiling", "111", 1);
		
		String creatorName = Manager.getInstance().getSessionManager(sessionId).getFB_name();
		ArrayList<String> invitedFriends = (ArrayList<String>)packet.getItem("Friends_id", ArrayList.class);

		Application.getInstance().getInvitationManager().addInvitation(Fbid,invitedFriends,sessionName,sessionId,creatorName);
		Packet returnPacket = new Packet("Add_Invitation_Response");
		returnPacket.addItems("Status", "OK");
		clientSH.sendCommand(returnPacket);
		// send ok?
		
//		if ( InvitationManager.invitations.containsKey(sessionId) )
//			invitation = InvitationManager.invitations.get(sessionId);
//		else {
//			invitation = new Invitation(Fbid,sessionName,sessionId);
//			InvitationManager.invitations.put(sessionId, invitation);
//		}
//		for (int friend: invitedFriends) {
//			if (!InvitationManager.ownInvitationLists.get(friend).containsKey(sessionId))
//				InvitationManager.ownInvitationLists.get(friend).put(sessionId, invitation);
//		}
	}

	
}

