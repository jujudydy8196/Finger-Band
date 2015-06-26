package fingerBand.connection;

import fingerBand.session.Session;

/**
 * Created by USER on 6/21/2015.
 */
public class SessionIDTask implements Task {

    public void handle(Packet p) {
        int session_id = p.getItem("Session_id",java.lang.Integer.class);
        Session.getInstance().receiveSession_id(session_id);
    }
}
