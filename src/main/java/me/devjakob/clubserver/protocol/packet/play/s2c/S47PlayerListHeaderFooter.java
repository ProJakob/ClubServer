package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;
import net.kyori.adventure.text.Component;

import java.io.IOException;

public class S47PlayerListHeaderFooter implements Packet {

	private final String header;
	private final String footer;

	public S47PlayerListHeaderFooter(Component header, Component footer) {
		this.header = Constants.SERIALIZER.serialize(header);
		this.footer = Constants.SERIALIZER.serialize(footer);
	}

	public S47PlayerListHeaderFooter() {
		this(Component.empty(), Component.empty());
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeString(buf, header);
		ProtocolUtil.writeString(buf, footer);
	}
}
