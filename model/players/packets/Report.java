package oberon.model.players.packets;

import oberon.model.players.Client;
import oberon.model.players.PacketType;
import oberon.model.players.packets.*;

public class Report implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		try {
			ReportHandler.handleReport(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}