package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C03Player implements Packet {

	public boolean onGround;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		onGround = buf.readBoolean();
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}
}
