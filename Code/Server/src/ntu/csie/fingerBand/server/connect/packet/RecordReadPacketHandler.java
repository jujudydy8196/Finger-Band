package ntu.csie.fingerBand.server.connect.packet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ntu.cise.fingerBand.DB.DbConnector;
import ntu.cise.fingerBand.DB.Record;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandler;
import ntu.csie.fingerBand.server.connect.RecordServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.databean.Music;

public class RecordReadPacketHandler extends PacketHandler {

	public RecordReadPacketHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Request_Record") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		RecordServiceHandler clientSH = (RecordServiceHandler)serviceHandler;
		int ssid = (Integer)packet.getItem("sessionId", Integer.class);
		
	

		DbConnector dbConn  = new DbConnector();
		try {
			dbConn.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		String recordData = dbConn.getRecordData(ssid);
		 Gson gson = new Gson();
		Packet returnPacket = new Packet("Request_Record_Response");
	
		HashMap<String,ArrayList<Music>> obj = gson.fromJson(recordData,new TypeToken<HashMap<String,ArrayList<Music>>>(){}.getType());
		returnPacket.addItems("Record", obj);
		clientSH.sendCommand(returnPacket);
	}

}
