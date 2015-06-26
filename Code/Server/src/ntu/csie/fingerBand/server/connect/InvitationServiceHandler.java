package ntu.csie.fingerBand.server.connect;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class InvitationServiceHandler extends ServiceHandler{


	public InvitationServiceHandler (SocketChannel _handle) {
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
	}
}
