package me.devjakob.clubserver.protocol;

import me.devjakob.clubserver.Main;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.Packets;

public class Broadcast {

	public static void broadcastPlay(Packet packet) {
		Main.channels.writeAndFlush(packet, channel -> channel.attr(ProtocolAttributes.PROTOCOL_STATE).get() == Packets.PLAY.stateId);
	}

}
