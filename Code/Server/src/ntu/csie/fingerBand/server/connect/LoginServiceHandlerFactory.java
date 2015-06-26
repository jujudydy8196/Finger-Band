package ntu.csie.fingerBand.server.connect;

import java.nio.channels.SocketChannel;
import ntu.csie.fingerBand.server.connect.packet.LoginPacketHandler;
import ntu.csie.fingerBand.server.connect.packet.PacketHandler;

public class LoginServiceHandlerFactory implements ServiceHandlerFactory {

	
	public ServiceHandler createServiceHandler(SocketChannel handle) {
		ServiceHandler serviceHandler = new LoginServiceHandler(handle);
		PacketHandler packetHandler = createPacketHandlerChain(serviceHandler);
		serviceHandler.setPacketHandler(packetHandler);
		return serviceHandler;
	}

	
	public PacketHandler createPacketHandlerChain(ServiceHandler serviceHandler) {

		PacketHandler login = new LoginPacketHandler(serviceHandler);
//		PacketHandler XXX = new XXXPacketHandler(serviceHandler);
//		LoginPacketHandler.setNextPacketHandler(XXX);

		return login;
	
	}

}
