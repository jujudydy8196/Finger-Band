package fingerBand.connection;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import fingerBand.Databean.Music;
import fingerBand.activities.MainActivity;
import fingerBand.record.RecordPlayer;

/**
 * Created by user on 2015/6/23.
 */
public class ReceiveRecordTask implements Task{

    private final static String tag = "ReceiveRecordTask";
    public final static String command = "Request_Record_Response";
    @Override
    public void handle(Packet p) {
        HashMap<String,ArrayList<Music>> record = (HashMap<String,ArrayList<Music>>) p.getItem("Record" , new TypeToken<HashMap<String,ArrayList<Music>>>(){}.getType());
        android.os.Message msg = new android.os.Message();
        Bundle bundle = new Bundle();
        bundle.putString("cmd", "playRecord");
        msg.setData(bundle);
        RecordPlayer.getInstance().setRecord(record);
        MainActivity.getInstance().UiHandler.sendMessage(msg);
    }
}
