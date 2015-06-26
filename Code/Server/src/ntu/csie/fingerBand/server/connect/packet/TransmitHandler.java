package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.databean.Music;
import ntu.csie.fingerBand.server.manager.SessionManager;
import ntu.csie.fingerBand.server.record.TrackRecorder;

public class TransmitHandler extends PacketHandler{
	private TrackRecorder trackRecorder;
	private SessionManager sessionManager;
	public TransmitHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
		// TODO Auto-generated constructor stub
	}
	public void setSessionManager(SessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	public void setTrackRecorder(TrackRecorder trackRecorder){
		this.trackRecorder = trackRecorder;
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		// TODO Auto-generated method stub
		return (packet.getCommand().equals("Transmit_message") || packet.getCommand().equals("Transmit_music"));
	}
	

	@Override
	protected void handle(Packet packet) {
		Music music = (Music)packet.getItem("Music", Music.class);
		sessionManager.notifyAll(music);
		trackRecorder.addRecord(music);
		
		
	}
	public void send(Music music){
		Packet pck = new Packet("Transmit_music_Receive");
		pck.addItems("Music", music);
		serviceHandler.sendCommand(pck);
	}
	public void end(){
		Packet pck = new Packet("Session_Over_Response");
		pck.addItems("Status", "END");
		serviceHandler.sendCommand(pck);
	
	}
	public void cutBind(){
		this.sessionManager=null;
	}
}
