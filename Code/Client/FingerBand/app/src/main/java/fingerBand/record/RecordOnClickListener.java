package fingerBand.record;

import android.view.View;
import android.view.View.OnClickListener;

public class RecordOnClickListener implements OnClickListener{
	private final static String tag = "Record/Click";
	private Record record;
	
	public RecordOnClickListener(Record record){
		this.record = record ;
	}
	
	@Override
	public void onClick(View v) {
        record.getRecord();
//		record.playRecord();
	}
}
