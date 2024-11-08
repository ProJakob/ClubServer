package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C06PlayerPositionLook extends C05PlayerLook implements Packet {

	public double x, y, z;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		super.readPacket(buf);
	}
}
