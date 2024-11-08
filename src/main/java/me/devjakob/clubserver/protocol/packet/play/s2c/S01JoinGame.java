package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S01JoinGame implements Packet {

	private final int entityId;
	private final byte gamemode;
	private final byte dimension;
	private final byte difficulty;
	private final short maxPlayers;
	private final String levelType;
	private final boolean reducedDebugInfo;

	public S01JoinGame(int entityId, byte gamemode, byte dimension, byte difficulty, short maxPlayers, String levelType, boolean reducedDebugInfo) {
		this.entityId = entityId;
		this.gamemode = gamemode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.maxPlayers = maxPlayers;
		this.levelType = levelType;
		this.reducedDebugInfo = reducedDebugInfo;
	}

	public S01JoinGame() {
		throw new IllegalStateException("Don't call this!");
	}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		buf.writeInt(entityId);
		buf.writeByte(gamemode);
		buf.writeByte(dimension);
		buf.writeByte(difficulty);
		buf.writeByte(maxPlayers);
		ProtocolUtil.writeString(buf, levelType);
		buf.writeBoolean(reducedDebugInfo);
	}
}
