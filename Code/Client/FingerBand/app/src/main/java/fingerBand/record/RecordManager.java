package fingerBand.record;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fingerBand.AndroidApplication;
import fingerBand.R;
import fingerBand.activities.MainActivity;
import fingerBand.connection.Packet;
import fingerBand.connection.ServiceHandler;

public class RecordManager {
	private final static String tag = "RecordManager";
	private final static RecordManager recordManager = new RecordManager();
	private MainActivity mainActivity;

    public void setRecordList(ArrayList<Record> recordList) {
        this.recordList = recordList;
    }

    private ArrayList<Record>  recordList;
	private boolean waitRespond ;
	private ServiceHandler recordHandler;
	
	private RecordManager (){
		this.mainActivity = MainActivity.getInstance();
		this.waitRespond = false;
        this.recordHandler = AndroidApplication.getInstance().getRecordHandler();
	}
	
	public static RecordManager getInstance(){
		return recordManager ; 
	}

	public View getRecordList(){

		Packet recordListPacket = new Packet("Request_Record_List");
		recordListPacket.addItems("FB_id", AndroidApplication.getInstance().getUser().getId());
		AndroidApplication.getInstance().getRecordHandler().write(recordListPacket);
        waitRespond = true;
        while(waitRespond){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
		LayoutInflater inflater = LayoutInflater.from(mainActivity);
		LinearLayout viewRecordLayout = (LinearLayout)inflater.inflate(R.layout.viewrecord_layout, null);
		LinearLayout recordListLayout = (LinearLayout)viewRecordLayout.findViewById(R.id.recordListLayout);
        if(recordList!=null) {
            for (int i = 0; i < recordList.size(); i++) {
                Record record = recordList.get(i);

                LinearLayout recordLayout = (LinearLayout) inflater.inflate(R.layout.record_layout, null);
                ((TextView) recordLayout.findViewById(R.id.recordName)).setText(record.getRecordName());
                recordLayout.setOnClickListener(new RecordOnClickListener(record));
                recordListLayout.addView(recordLayout);
                ((LinearLayout.LayoutParams) recordLayout.getLayoutParams()).setMargins(10, 20, 10, 20);

            }
        }
		return viewRecordLayout;
	}

    public void respond(){
        waitRespond = false;
    }

}
