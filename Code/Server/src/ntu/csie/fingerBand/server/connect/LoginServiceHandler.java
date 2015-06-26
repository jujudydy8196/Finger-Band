package ntu.csie.fingerBand.server.connect;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public class LoginServiceHandler extends ServiceHandler {

//	private User user;

	public LoginServiceHandler(SocketChannel _handle) {
		super(_handle);
	}
	
	@Override
	public void open() {
		Reactor reactor = Reactor.getInstance();
		reactor.registerHandler(this, SelectionKey.OP_READ);
	}

//	public User getUser(){
//		return user;
//	}
//	
//	public void setUser(User _user){
//		user = _user;
//	}

	@Override
	public void endService() {
		Reactor.getInstance().cancelRegister(this);
	}
}
