package fingerBand.invite;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import fingerBand.AndroidApplication;
import fingerBand.R;
import fingerBand.activities.MainActivity;
import fingerBand.connection.Packet;
import fingerBand.session.Session;

public class InvitationOnClickListener implements OnClickListener{
	private final static String tag = "InvitationClick";
	private Invitation invitation;
	public InvitationOnClickListener(Invitation invitation){
		this.invitation = invitation ;
	}
	@Override
	public void onClick(View v) {
		Button btn = (Button) v;
		if (btn.getContentDescription().equals("accept") )  {
			Packet respondInvitationListPacket = new Packet("Join_Session");
			respondInvitationListPacket.addItems("FB_id", AndroidApplication.getInstance().getUser().getId());
			respondInvitationListPacket.addItems("Session_id", invitation.getSessionID());
			AndroidApplication.getInstance().getInvitationHandler().write(respondInvitationListPacket);
			Session.getInstance().joinSession(AndroidApplication.getInstance().getUser().getId(), invitation.getSessionID());
            ((Button)MainActivity.getInstance().findViewById(R.id.createSessionBtn)).setText("L");
            MainActivity.getInstance().goKeyBoard();

			Log.v(tag,"accept");
		}
		else if(btn.getContentDescription().equals("reject") ) {
			Log.v(tag,"reject");
		}


	}

}
