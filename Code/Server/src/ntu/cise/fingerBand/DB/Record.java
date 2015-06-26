package ntu.cise.fingerBand.DB;

import java.util.ArrayList;

public class Record {

	// attributes:
	int sessionId;
	String recordName;
	
	// constructor
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
	
	public int getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	

}
