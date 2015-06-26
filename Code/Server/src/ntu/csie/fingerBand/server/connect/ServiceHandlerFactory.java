package ntu.csie.fingerBand.server.connect;

import java.nio.channels.SocketChannel;

import ntu.csie.fingerBand.server.connect.packet.PacketHandler;

public interface ServiceHandlerFactory {
	public ServiceHandler createServiceHandler(SocketChannel handle);
	public PacketHandler createPacketHandlerChain(ServiceHandler serviceHandler);
}
