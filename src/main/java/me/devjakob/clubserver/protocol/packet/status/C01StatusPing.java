package me.devjakob.clubserver.protocol.packet.status;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C01StatusPing implements Packet {

	public long payload;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		payload = buf.readLong();
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}
}
