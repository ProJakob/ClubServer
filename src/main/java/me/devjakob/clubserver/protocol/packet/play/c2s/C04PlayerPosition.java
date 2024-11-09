package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C04PlayerPosition implements Packet {

	public double x, y, z;
	public boolean onGround;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		onGround = buf.readBoolean();
	}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {

	}

}
