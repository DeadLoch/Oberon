package oberon.model.players.packets;

import oberon.model.items.Item;
import oberon.model.players.Client;
import oberon.model.players.PacketType;
import oberon.model.players.PlayerHandler;
import oberon.util.Misc;

public class ItemOnPlayer implements PacketType {
	
	private static final int[] CRACKER_ITEMS = { 1038, 1040, 1042, 1044, 1046, 1048 };
	
	@Override
	public void processPacket(final Client c, final int packetType, final int packetSize) {
		@SuppressWarnings("unused")
		int a = c.getInStream().readUnsignedWordBigEndianA();
		int playerIndex = c.getInStream().readUnsignedWord();
		int item = c.getInStream().readUnsignedWord();
		int slot = c.getInStream().readUnsignedWordBigEndian();
		
		// Incase of editted client
		if(playerIndex > PlayerHandler.players.length) {
			return;
		}
		// Make sure its a player online
		if(PlayerHandler.players[playerIndex] == null) {
			return;
		}
		// Make sure the player has the item
		if(!c.getItems().playerHasItem(item, 1, slot)) {
			return;
		}
		
		Client other = (Client)PlayerHandler.players[playerIndex];
		switch(item) {
		case 962:
			if(other.getItems().freeSlots() <= 0) {
				c.sendMessage("Other player does not have enough free inventory space.");
				return;
			}
			int player = Misc.random(1);
			int prize = CRACKER_ITEMS[(int) (Math.random() * CRACKER_ITEMS.length)];
			(player == 0 ? c : other).getItems().addItem(prize, 1);
			(player == 0 ? c : other).sendMessage("You pull the cracker and win a " + Item.getItemName(prize) + ".");
			(player == 0 ? other : c).sendMessage("You pull the cracker and win nothing. The other player won a " + Item.getItemName(prize) + ".");
			c.getItems().deleteItem2(962, 1);
			break;
		default:
			c.sendMessage("Nothing interesting happenes.");
			break;
		}
	}
}	