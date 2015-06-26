package fingerBand.connection;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import fingerBand.invite.Invitation;
import fingerBand.invite.InviteManager;

public class ReceiveInvitationTask implements Task{
	private final static String tag = "ReceiveInvitationTask";
	public final static String command = "Request_Invitation_Response";
	@Override
	public void handle(Packet p) {
		ArrayList<Invitation> invitationList = (ArrayList<Invitation>) p.getItem("Invitation_List" , new TypeToken<ArrayList<Invitation>>(){}.getType());
        if(invitationList != null ) Log.v("ReceiveInvitationTask", "get"+invitationList.size());
		InviteManager.getInstance().setInvitationList(invitationList);
		InviteManager.getInstance().respond();
	}

}
