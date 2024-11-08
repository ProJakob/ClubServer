package me.devjakob.clubserver.protocol.packet.status;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S00StatusResponse implements Packet {

	private final String response;

	public S00StatusResponse(String response) {
		this.response = response;
	}

	public S00StatusResponse() {
		this("{\"version\":{\"name\":\"Unknown\",\"protocol\":-1}}");
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeString(buf, response);
	}
}
