package oberon.model.players.packets;

import oberon.Server;
import oberon.model.players.Client;
import oberon.model.players.PacketType;

/**
 * Challenge Player
 **/
public class ChallengePlayer implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {		
		switch(packetType) {
			case 128:
			int answerPlayer = c.getInStream().readUnsignedWord();
			if(Server.playerHandler.players[answerPlayer] == null) {
				return;
			}			
			
			if(c.arenas() || c.duelStatus == 5) {
				c.sendMessage("You can't challenge inside the arena!");
				return;
			}

			c.getTradeAndDuel().requestDuel(answerPlayer);
			break;
		}		
	}	
}
