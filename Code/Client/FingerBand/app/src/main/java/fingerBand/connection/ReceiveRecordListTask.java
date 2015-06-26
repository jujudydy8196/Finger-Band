package fingerBand.connection;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import fingerBand.record.Record;
import fingerBand.record.RecordManager;

/**
 * Created by user on 2015/6/23.
 */
public class ReceiveRecordListTask implements Task{

        private final static String tag = "ReceiveRecordListTask";
        public final static String command = "Request_Record_List_Response";
        @Override
        public void handle(Packet p) {
            ArrayList<Record> recordList = (ArrayList<Record>) p.getItem("RecordList" , new TypeToken<ArrayList<Record>>(){}.getType());
            if(recordList != null ) Log.v(tag, "get" + recordList.size());
            RecordManager.getInstance().setRecordList(recordList);
            RecordManager.getInstance().respond();
        }

}
