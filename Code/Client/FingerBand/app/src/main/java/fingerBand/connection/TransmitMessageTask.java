package fingerBand.connection;

import fingerBand.Databean.Message;
import fingerBand.session.Session;

/**
 * Created by USER on 6/21/2015.
 */
public class TransmitMessageTask implements Task {
    public void handle(Packet p){
        Session.getInstance().receiveMsg(p.getItem("Message", Message.class));
    }
}
