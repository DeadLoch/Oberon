package oberon.model.players.packets;

import oberon.Server;
import oberon.model.players.Client;
import oberon.model.players.PacketType;
import oberon.util.Misc;
/**
 * Chat
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readQWord());
		textSent = textSent.replaceAll("_", " ");
		//c.sendMessage(textSent);
		Server.clanChat.handleClanChat(c, textSent);
	}	
}
