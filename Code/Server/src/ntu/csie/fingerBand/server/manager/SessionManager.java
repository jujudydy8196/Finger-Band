package ntu.csie.fingerBand.server.manager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ntu.cise.fingerBand.DB.DbConnector;
import ntu.csie.fingerBand.server.connect.packet.TransmitHandler;
import ntu.csie.fingerBand.server.databean.Music;
import ntu.csie.fingerBand.server.record.TrackRecorder;

import com.google.gson.Gson;

public class SessionManager {
	private HashMap<String, TransmitHandler> memberTracks = new HashMap<String, TransmitHandler>();
	private HashMap<String,TrackRecorder> memberRecord = new HashMap<String, TrackRecorder>();
	private int Session_id;
	private Date startDate;
	public Date getStartDate() {
		return startDate;
	}

	
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	private String FB_name;
	private String FB_id;
	private ArrayList<String> soundInfo;
	
	public HashMap<String,TrackRecorder> getMemberRecord(){
		return memberRecord;
	}
	
	public TrackRecorder getTrackRecorder(String FB_id){
		if(memberRecord.containsKey(FB_id))
			return memberRecord.get(FB_id);
		else{
			TrackRecorder trr = new TrackRecorder(startDate);
			memberRecord.put(FB_id, trr);
			return trr;
		}
	}
	
	public SessionManager(String FB_name, String FB_id, int Session_id){
		this.setSession_id(Session_id);
		this.soundInfo = new ArrayList<String>();
		this.FB_name = FB_name;
		this.FB_id = FB_id;
	}
	public void addTrack(String FB_id, TransmitHandler transmitHandler){
		memberTracks.put(FB_id, transmitHandler);
	}
	public TransmitHandler getTrack(String FB_id){
		return memberTracks.get(FB_id);
	}
	public void deleteTrack(String name){
		memberTracks.remove(name);	
	}
	public void notifyAll(Music music){
		Iterator it = memberTracks.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Entry) it.next();
			System.out.println("FB ID"+music.getFB_id());
			System.out.println("transmithandler" +pair.getKey() );
			if(!pair.getKey().equals(music.getFB_id()))
				 ((TransmitHandler) pair.getValue()).send(music);
		}
	}
	public void endSession(){
		
		Iterator it = memberTracks.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Entry) it.next();
				 ((TransmitHandler) pair.getValue()).end();
		}
		Gson gson = new Gson();
		
		String record = gson.toJson(getMemberRecord());
		DbConnector dbConn = new DbConnector();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dbConn.getConnection();
			dbConn.setSessionRecord(getSession_id(), dateFormat.format(startDate)+getSession_id(), record);
			dbConn.endConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		memberTracks.clear();
		//IO write into database
	}
	public String getFB_id(){
		return FB_id;
	}
	public String getFB_name(){
		return FB_name;
	}

	public int getSession_id() {
		return Session_id;
	}

	public void setSession_id(int session_id) {
		Session_id = session_id;
	}
	
	
	
}
