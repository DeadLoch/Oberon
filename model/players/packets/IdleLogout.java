package oberon.model.players.packets;


import oberon.model.players.Client;
import oberon.model.players.PacketType;


public class IdleLogout implements PacketType {
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		//if (!c.playerName.equalsIgnoreCase("Sanity"))
			//c.logout();
	}
}