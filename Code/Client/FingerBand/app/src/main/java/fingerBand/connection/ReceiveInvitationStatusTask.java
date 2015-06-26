package fingerBand.connection;

import android.util.Log;


public class ReceiveInvitationStatusTask implements Task{
	private final static String tag = "ReceiveInvitationStatusTask";
	public final static String command = "Join_Session_Response";
	@Override
	public void handle(Packet p) {

		String status = p.getItem("Status", String.class);
		Log.v("tag",status);
		//InviteManager.getInstance().respond(true);
	}

}
