package me.devjakob.clubserver.protocol.packet.login;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class S02LoginSuccess implements Packet {

	private final String uuid;
	private final String username;

	public S02LoginSuccess(UUID uuid, String username) {
		this.uuid = uuid.toString();
		this.username = username;
	}

	public S02LoginSuccess(String username) {
		this(UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8)), username);
	}

	public S02LoginSuccess() {
		this(UUID.nameUUIDFromBytes(("OfflinePlayer:" + "ProJakob").getBytes(StandardCharsets.UTF_8)), "ProJakob");
	}

	public S02LoginSuccess applyTo(Channel channel) {
		channel.attr(ProtocolAttributes.CONNECTION_INFO)
				.get().setName(username).setUuid(UUID.fromString(uuid));
		return this;
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		ProtocolUtil.writeString(buf, uuid);
		ProtocolUtil.writeString(buf, username);
	}
}
