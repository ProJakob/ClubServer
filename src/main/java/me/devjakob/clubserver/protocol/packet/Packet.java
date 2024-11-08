package me.devjakob.clubserver.protocol.packet;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

public interface Packet {

	void readPacket(ByteBuf buf) throws IOException;
	void writePacket(ByteBuf buf) throws IOException;

}
