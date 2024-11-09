package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C05PlayerLook implements Packet {

	public float yaw, pitch;
	public boolean onGround;

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		yaw = buf.readFloat();
		pitch = buf.readFloat();
		onGround = buf.readBoolean();
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}

}
