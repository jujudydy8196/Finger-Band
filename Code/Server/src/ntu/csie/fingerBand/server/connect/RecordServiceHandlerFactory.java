package ntu.csie.fingerBand.server.connect;

import java.nio.channels.SocketChannel;

import ntu.csie.fingerBand.server.connect.packet.PacketHandler;
import ntu.csie.fingerBand.server.connect.packet.RecordReadListPacketHandler;
import ntu.csie.fingerBand.server.connect.packet.RecordReadPacketHandler;



public class RecordServiceHandlerFactory implements ServiceHandlerFactory {

	

	public ServiceHandler createServiceHandler(SocketChannel handle) {
		ServiceHandler serviceHandler = new RecordServiceHandler(handle);
		PacketHandler packetHandler = createPacketHandlerChain(serviceHandler);
		serviceHandler.setPacketHandler(packetHandler);
		return serviceHandler;
	}


	public PacketHandler createPacketHandlerChain(ServiceHandler serviceHandler) {
		PacketHandler readRecordList = new RecordReadListPacketHandler(serviceHandler);
		PacketHandler readRecord = new RecordReadPacketHandler(serviceHandler);

		readRecordList.setNextPacketHandler(readRecord);
		
		return  readRecordList ;
		
		
	}

}
