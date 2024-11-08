package me.devjakob.clubserver.protocol.packet.login;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;
import net.kyori.adventure.text.Component;

import java.io.IOException;

public class S00Disconnect implements Packet {

	private final String reason;

	public S00Disconnect(Component reason) {
		this.reason = Constants.SERIALIZER.serialize(reason);
	}

	public S00Disconnect() {
		this(Component.text("Disconnected"));
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeString(buf, reason);
	}
}
