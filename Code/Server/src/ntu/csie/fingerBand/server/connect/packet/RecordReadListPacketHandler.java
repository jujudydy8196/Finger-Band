package ntu.csie.fingerBand.server.connect.packet;

import java.sql.SQLException;
import java.util.ArrayList;

import ntu.cise.fingerBand.DB.DbConnector;
import ntu.cise.fingerBand.DB.Record;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandler;
import ntu.csie.fingerBand.server.connect.RecordServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;

public class RecordReadListPacketHandler extends PacketHandler {

	public RecordReadListPacketHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Request_Record_List") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		RecordServiceHandler clientSH = (RecordServiceHandler)serviceHandler;
		String fbid = (String)packet.getItem("FB_id", String.class);
		
	

		DbConnector dbConn = new DbConnector();
		try {
			dbConn.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		ArrayList<Record> myRecordList = dbConn.getRecordList(fbid);
		
		Packet returnPacket = new Packet("Request_Record_List_Response");
		//?
		returnPacket.addItems("Invitation_List", myRecordList);
		clientSH.sendCommand(returnPacket);
	}

}
