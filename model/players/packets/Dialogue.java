package oberon.model.players.packets;

import oberon.model.players.Client;
import oberon.model.players.PacketType;


/**
 * Dialogue
 **/
public class Dialogue implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.nextChat > 0) {
			if (c.normalChat == true) {
				c.getDH().sendDialogues(c.nextChat, c.talkingNpc);
			} else if (c.quest1Chat == true) {
				c.getDH().sendQuestDialogue(c.nextChat, c.talkingNpc);
			}	
			
		}  else {
			c.getDH().sendDialogues(0, -1);
		}		
	}
}
		