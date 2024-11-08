package me.devjakob.clubserver.protocol.packet.login;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C00LoginStart implements Packet {

	public String name;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		name = ProtocolUtil.readString(buf, 16);
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}
}
