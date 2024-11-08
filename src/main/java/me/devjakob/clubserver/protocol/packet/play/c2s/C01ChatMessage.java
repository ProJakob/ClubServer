package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C01ChatMessage implements Packet {

	public String message;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		message = ProtocolUtil.readString(buf, 100);
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}
}
