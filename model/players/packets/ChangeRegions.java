package oberon.model.players.packets;

import oberon.Server;
import oberon.model.players.Client;
import oberon.model.players.PacketType;

/**
 * Change Regions
 */
public class ChangeRegions implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		//Server.objectHandler.updateObjects(c);
		Server.itemHandler.reloadItems(c);
		Server.objectManager.loadObjects(c);
		c.getPA().castleWarsObjects();
		c.getPA().removeObjects();
		
		c.saveFile = true;
		
		if(c.skullTimer > 0) {
			c.isSkulled = true;	
			c.headIconPk = 0;
			c.getPA().requestUpdates();
		}

	}
		
}
