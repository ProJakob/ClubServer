package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.io.IOException;

public class S40Disconnect implements Packet {

	private final String reason;

	public S40Disconnect(Component reason) {
		this.reason = Constants.SERIALIZER.serialize(reason);
	}

	public S40Disconnect() {
		this(Component.text("No reason given").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC));
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeString(buf, reason);
	}
}
