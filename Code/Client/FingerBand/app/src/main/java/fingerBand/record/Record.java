package fingerBand.record;

import android.util.Log;

import fingerBand.AndroidApplication;
import fingerBand.connection.Packet;

public class Record {
	private String recordName;
	private int sessionId;
	public void getRecord(){
        Packet recordListPacket = new Packet("Request_Record");
        recordListPacket.addItems("sessionId", sessionId);
        AndroidApplication.getInstance().getRecordHandler().write(recordListPacket);
        Log.v("Record", "GetRecord");
	}
	public void playRecord(){
		Log.v("Record", "PlayRecord");
	}
    public Record(String recordName, int sessionId){
        this.recordName = recordName;
        this.sessionId = sessionId;
    }
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public int  getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
}
