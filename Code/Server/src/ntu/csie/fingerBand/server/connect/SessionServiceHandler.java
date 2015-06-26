package ntu.csie.fingerBand.server.connect;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public class SessionServiceHandler extends ServiceHandler {

//	private Session session;

	public SessionServiceHandler(SocketChannel _handle) {
		super(_handle);
	}

	@Override
	public void open() {
		Reactor reactor = Reactor.getInstance();
		reactor.registerHandler(this, SelectionKey.OP_READ);
	}
	

	@Override
	public void endService() {
		Reactor.getInstance().cancelRegister(this);
		//clear session
	}
	
	

}
