package ntu.csie.fingerBand.server;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;

import ntu.csie.fingerBand.server.connect.Acceptor;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandlerFactory;
import ntu.csie.fingerBand.server.connect.LoginServiceHandlerFactory;
import ntu.csie.fingerBand.server.connect.Reactor;
import ntu.csie.fingerBand.server.connect.RecordServiceHandlerFactory;
import ntu.csie.fingerBand.server.connect.SessionServiceHandlerFactory;

public class Main {

    
    
    
	public static void init(){
		Application.init();
	}
	
	public static void main(String[] args){
		init();

		System.out.println("Start to serve");
		Application.getInstance().startReactor();
		
	}
}
