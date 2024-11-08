package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;

import java.io.IOException;

public class S02ChatMessage implements Packet {

	private final String message;
	private final byte position;

	public S02ChatMessage(Component message, byte position) {
		this.message = Constants.SERIALIZER.serialize(message);
		this.position = position;
	}

	public S02ChatMessage() {
		this(Component.text("No chat message specified?!").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC), (byte) 1);
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeString(buf, message);
		buf.writeByte(position);
	}
}
