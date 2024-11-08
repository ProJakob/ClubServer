package me.devjakob.clubserver.protocol.packet.handshaking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

import static me.devjakob.clubserver.protocol.ProtocolUtil.*;

public class C00Handshake implements Packet {

	public int protocolVersion;
	public String serverAddress;
	public int serverPort;
	public int nextState;

	public void applyTo(Channel channel) {
		channel.attr(ProtocolAttributes.CONNECTION_INFO)
				.get()
				.setProtocolVersion(protocolVersion)
				.setServerAddress(serverAddress)
				.setServerPort(serverPort);
	}

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		protocolVersion = readVarInt(buf);
		serverAddress = readString(buf, 255);
		serverPort = buf.readUnsignedShort();
		nextState = readVarInt(buf);
	}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		// We never send this packet, no need to implement this.
	}
}
