package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;
import java.util.Arrays;

public class S26MapChunkBulk implements Packet {

    private final boolean skyLightSent;
    private final S21ChunkData[] chunkData;

    public S26MapChunkBulk(boolean skyLightSent, S21ChunkData[] chunkData) {
        this.skyLightSent = skyLightSent;
        this.chunkData = chunkData;
    }

    public S26MapChunkBulk() {
        throw new IllegalStateException("Don't call this!");
    }

    @Override
    public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        buf.writeBoolean(true);
        buf.writeInt(chunkData.length);
        for (S21ChunkData chunkDatum : chunkData) {
            buf.writeInt(chunkDatum.chunkX);
            buf.writeInt(chunkDatum.chunkZ);
            buf.writeShort((short)(chunkDatum.primaryBitMask & 65535));
        }
        for (S21ChunkData chunkDatum : chunkData) {
            buf.writeBytes(chunkDatum.data);
        }
    }

    @Override
    public String toString() {
        return "S26MapChunkBulk{" +
                "skyLightSent=" + skyLightSent +
                ", chunkData=" + Arrays.toString(chunkData) +
                '}';
    }
}
