package me.devjakob.clubserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.devjakob.clubserver.protocol.packet.play.s2c.S21ChunkData;

public class Util {

    public static S21ChunkData prepareFlatPacket(int chunkX, int chunkZ) {
        ByteBuf buf = Unpooled.buffer();
        writeFlatPlaneChunk(buf);

        byte[] b = new byte[buf.readableBytes()];
        buf.readBytes(b);

        return new S21ChunkData(chunkX, chunkZ, true, 0b1, b);
    }

    public static void writeFlatPlaneChunk(ByteBuf buf) {
        int t = 1;
        for (int i = 0; i < 16 * 16 * 16; i++) {
            t = t == 1 ? 0 : 1;
            buf.writeByte(t & 255);
            buf.writeByte(t >> 8);
        }

        for (int i = 0; i < 4096; i++) {
            buf.writeByte(5);
        }

        for (int i = 0; i < 4096; i++) {
            buf.writeByte(5);
        }

        for (int i = 0; i < 256; i++) {
            buf.writeByte(1);
        }
    }

}
