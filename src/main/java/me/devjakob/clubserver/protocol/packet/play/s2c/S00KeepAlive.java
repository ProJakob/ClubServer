package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S00KeepAlive implements Packet {

	private final int keepAliveId;

	public S00KeepAlive(int keepAliveId) {
		this.keepAliveId = keepAliveId;
	}

	public S00KeepAlive() {
		this(0);
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeVarInt(buf, keepAliveId);
	}
}
