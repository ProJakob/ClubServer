package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.util.BlockPos;

import java.io.IOException;

public class S05SpawnPosition implements Packet {

	private BlockPos spawnPosition;

	public S05SpawnPosition(BlockPos spawnPosition) {
		this.spawnPosition = spawnPosition;
	}

	public S05SpawnPosition() {}

	@Override public void readPacket(ByteBuf buf) throws IOException {}

	@Override
	public void writePacket(ByteBuf buf) throws IOException {
		buf.writeLong(spawnPosition.toLong());
	}
}
