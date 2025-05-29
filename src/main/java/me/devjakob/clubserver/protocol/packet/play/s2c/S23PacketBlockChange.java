package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.util.BlockPos;

import java.io.IOException;

public class S23PacketBlockChange implements Packet {

    private final long pos;
    private final char blockState;

    public S23PacketBlockChange(BlockPos pos, char blockState) {
        this.pos = pos.toLong();
        this.blockState = blockState;
    }

    public S23PacketBlockChange() {
        throw new IllegalCallerException();
    }

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        buf.writeLong(this.pos);
        ProtocolUtil.writeVarInt(buf, blockState);
    }
}
