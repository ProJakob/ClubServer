package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S21ChunkData implements Packet {

    private final int chunkX, chunkZ;
    private final boolean groundUpContinuous;
    private final int primaryBitMask;
    private final byte[] data;

    public S21ChunkData(int chunkX, int chunkZ, boolean groundUpContinuous, int primaryBitMask, byte[] data) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.groundUpContinuous = groundUpContinuous;
        this.primaryBitMask = primaryBitMask;
        this.data = data;
    }

    public S21ChunkData() {
        throw new IllegalStateException("Don't call this!");
    }

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        buf.writeInt(this.chunkX);
        buf.writeInt(this.chunkZ);
        buf.writeBoolean(this.groundUpContinuous);
        buf.writeShort((short)(primaryBitMask & 65535));
        ProtocolUtil.writeByteArray(buf, data);
    }
}
