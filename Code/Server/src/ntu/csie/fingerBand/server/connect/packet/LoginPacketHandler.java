package ntu.csie.fingerBand.server.connect.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.UUID;

import ntu.cise.fingerBand.DB.DbConnector;
import ntu.csie.fingerBand.server.connect.LoginServiceHandler;
import ntu.csie.fingerBand.server.connect.ServiceHandler;
import ntu.csie.fingerBand.server.databean.User;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class LoginPacketHandler extends PacketHandler {
	private String STATUS;
	public LoginPacketHandler(ServiceHandler _serviceHandler) {
		super(_serviceHandler);
	}

	@Override
	protected boolean isResponsible(Packet packet) {
		return packet.getCommand().compareTo("Login") == 0;
	}

	@Override
	protected void handle(Packet packet) {
		LoginServiceHandler clientSH = (LoginServiceHandler)serviceHandler;
		User user = (User) packet.getItem("User",User.class);
		
		String name = user.getName();
		String id = user.getId();
		//建立第一個 account 
		DbConnector dbConn = new DbConnector();
		try {
			dbConn.getConnection();
			dbConn.createAccount(id, name);
			dbConn.endConnection();
			STATUS="OK";
			//Store the id to database, if sucess, STATUS="OK"
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			STATUS="fail";
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			STATUS="fail";
			e.printStackTrace();
		}
		Packet returnPacket = new Packet("Login_Response");
		returnPacket.addItems("Status", STATUS);
		clientSH.sendCommand(returnPacket);
	}

}
