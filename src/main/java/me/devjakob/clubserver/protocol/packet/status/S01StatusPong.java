package me.devjakob.clubserver.protocol.packet.status;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S01StatusPong implements Packet {

	public final long payload;

	public S01StatusPong(long payload) {
		this.payload = payload;
	}

	public S01StatusPong() {
		this(-1);
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		buf.writeLong(payload);
	}
}
