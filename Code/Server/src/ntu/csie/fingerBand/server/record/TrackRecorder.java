package ntu.csie.fingerBand.server.record;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import ntu.csie.fingerBand.server.databean.Music;

public class TrackRecorder {
	private Date startTime;
	private ArrayList<Music> record = new  ArrayList<Music>();
	
	
	public TrackRecorder(Date startTime){
		this.startTime = startTime;
	}
	
	

	public ArrayList<Music> getRecord() {
		return record;
	}

	public void addRecord(Music music) {
		Date receiveTime = new Date();
		music.setTimeStamp(receiveTime.getTime()-startTime.getTime());
		record.add(music);
	}
	
	
	
	
	
}
