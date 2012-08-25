package oberon.model.players.packets;

import oberon.Server;
import oberon.model.items.UseItem;
import oberon.model.players.Client;
import oberon.model.players.PacketType;


public class ItemOnNpc implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = Server.npcHandler.npcs[i].npcType;
				if(!c.getItems().playerHasItem(itemId, 1, slot)) {
			return;
		}
		
		UseItem.ItemonNpc(c, itemId, npcId, slot);
	}
}
