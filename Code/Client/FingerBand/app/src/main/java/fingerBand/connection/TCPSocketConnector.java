package fingerBand.connection;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;

public class TCPSocketConnector extends Connector{

	public TCPSocketConnector(TCPSocketHandle handle) {
		super(handle);
		
	}

	public boolean connect( InetAddress remoteAddr, int remotePort, String mode) throws IOException {
		Log.d("TCPConnector","I-TCPConnector is Connecting");
		this.mode=mode;
		handle.init(remoteAddr, remotePort);
		if(handle.connect()){
            return true;
		}else{
			return false;
		}
	}

	
	
}
