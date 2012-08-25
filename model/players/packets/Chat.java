package oberon.model.players.packets;

import oberon.Connection;
import oberon.model.players.Client;
import oberon.model.players.PacketType;

/**
 * Chat
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
		ReportHandler.addText(c.playerName, c.getChatText(), packetSize - 2);
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
		if(System.currentTimeMillis() < c.muteEnd) {
		c.sendMessage("You are muted");
		return;
		} else {
		c.muteEnd = 0;
		}
		if (!Connection.isMuted(c))
			c.setChatTextUpdateRequired(true);
	}	
}
