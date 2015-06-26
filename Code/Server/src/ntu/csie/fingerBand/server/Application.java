package ntu.csie.fingerBand.server;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;

import ntu.csie.fingerBand.server.connect.Acceptor;
import ntu.csie.fingerBand.server.connect.InvitationServiceHandlerFactory;
import ntu.csie.fingerBand.server.connect.LoginServiceHandlerFactory;
import ntu.csie.fingerBand.server.connect.Reactor;
import ntu.csie.fingerBand.server.connect.RecordServiceHandlerFactory;
import ntu.csie.fingerBand.server.connect.SessionServiceHandlerFactory;
import ntu.csie.fingerBand.server.invitation.InvitationManager;

public class Application {
    final static int LOGIN_PORT = 1120;
    final static int INVITATION_PORT = 1121;
    final static int SESSION_PORT = 1122;
    final static int RECORD_PORT = 1123;
	private static Application sInstance ;
	private Reactor reactor;
	private InvitationManager invitationManager;
	private Acceptor loginAcceptor;
	private Acceptor invitationAcceptor;
	private Acceptor sessionAcceptor;
	private Acceptor recordAcceptor;
	
	private Application(){
		initializeInstance();
	}
	
	public static void init(){
		sInstance = new Application();
	}
	
	public static Application getInstance(){
        if(sInstance==null){
        	sInstance = new Application();
		}
        return sInstance;
    }

	protected void initializeInstance(){
		invitationManager = new InvitationManager();
		reactor = Reactor.getInstance();
		loginAcceptor = new Acceptor(new LoginServiceHandlerFactory());
		loginAcceptor.open(new InetSocketAddress("0.0.0.0",LOGIN_PORT));
		reactor.registerHandler(loginAcceptor, SelectionKey.OP_ACCEPT);
		
		invitationAcceptor = new Acceptor(new InvitationServiceHandlerFactory());
		invitationAcceptor.open(new InetSocketAddress("0.0.0.0",INVITATION_PORT));
		reactor.registerHandler(invitationAcceptor, SelectionKey.OP_ACCEPT);
		
		sessionAcceptor = new Acceptor(new SessionServiceHandlerFactory());
		sessionAcceptor.open(new InetSocketAddress("0.0.0.0",SESSION_PORT));
		reactor.registerHandler(sessionAcceptor, SelectionKey.OP_ACCEPT);
		
		recordAcceptor = new Acceptor(new RecordServiceHandlerFactory());
		recordAcceptor.open(new InetSocketAddress("0.0.0.0",RECORD_PORT));
		reactor.registerHandler(recordAcceptor, SelectionKey.OP_ACCEPT);
	}

	public InvitationManager getInvitationManager(){
		return invitationManager;
	}
	
	public void startReactor(){
		reactor.run();
	}
}


