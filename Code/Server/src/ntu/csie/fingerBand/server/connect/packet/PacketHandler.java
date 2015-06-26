package ntu.csie.fingerBand.server.connect.packet;

import ntu.csie.fingerBand.server.connect.NoHandlerSupportedException;
import ntu.csie.fingerBand.server.connect.ServiceHandler;




public abstract class PacketHandler {
	private PacketHandler nextPacketHandler;
	protected ServiceHandler serviceHandler;
	public PacketHandler(ServiceHandler _serviceHandler){
		nextPacketHandler = null;
		serviceHandler = _serviceHandler;
	}
	public void setNextPacketHandler(PacketHandler _nextPacketHandler){
		nextPacketHandler = _nextPacketHandler;
	}public PacketHandler getNextPacketHandler(){
		return nextPacketHandler; 
	}
	public void handlePacket(Packet packet) throws NoHandlerSupportedException{
		if(isResponsible(packet)){
			handle(packet);
		}else if(nextPacketHandler != null){
			nextPacketHandler.handlePacket(packet);
		}else{
			throw new NoHandlerSupportedException();
		}
	}
	protected abstract boolean isResponsible(Packet packet);
	protected abstract void handle(Packet packet);
}
