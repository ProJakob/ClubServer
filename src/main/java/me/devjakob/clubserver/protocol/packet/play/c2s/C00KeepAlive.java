package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C00KeepAlive implements Packet {

	public long keepAliveId;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		keepAliveId = ProtocolUtil.readVarInt(buf);
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}
}
