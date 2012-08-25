package oberon.model.players.packets;

import oberon.model.players.Client;
import oberon.model.players.PacketType;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
			
	}	
}
