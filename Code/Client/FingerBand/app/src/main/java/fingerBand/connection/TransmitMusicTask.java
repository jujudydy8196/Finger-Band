package fingerBand.connection;

import android.util.Log;

import fingerBand.Databean.Music;
import fingerBand.session.Session;


public class TransmitMusicTask implements Task {
    public void handle(Packet p){
        Session.getInstance().receiveSound(p.getItem("Music",Music.class));
        Log.v("tramsmit Music","hi");
    }
}
