package me.devjakob.clubserver.protocol.packet.status;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C00StatusRequest implements Packet {

	@Override public void readPacket(ByteBuf buf) throws IOException {}
	@Override public void writePacket(ByteBuf buf) throws IOException {}

}
