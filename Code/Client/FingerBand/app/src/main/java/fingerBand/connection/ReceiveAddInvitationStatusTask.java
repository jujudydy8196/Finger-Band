package fingerBand.connection;

import android.util.Log;

/**
 * Created by user on 2015/6/22.
 */
public class ReceiveAddInvitationStatusTask implements Task{
    private final static String tag = "ReceiveAddInvitationStatusTask";
    public final static String command = "Add_Invitation_Response";
    @Override
    public void handle(Packet p) {

            String status = p.getItem("Status", String.class);
            Log.v("tag", status);
            //InviteManager.getInstance().respond(true);
    }
}
