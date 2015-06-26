package fingerBand.connection;

import fingerBand.session.Session;

/**
 * Created by USER on 6/21/2015.
 */
public class SessionOverTask implements Task {
    public void handle(Packet p){
        Session.getInstance().sessionEnd();
    }
}
