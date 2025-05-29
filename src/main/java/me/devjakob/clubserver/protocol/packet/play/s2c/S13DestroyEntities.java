package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S13DestroyEntities implements Packet {

    private final int[] entityIds;

    public S13DestroyEntities(int... entityIds) {
        this.entityIds = entityIds;
    }

    public S13DestroyEntities() {
        throw new IllegalCallerException();
    }

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        ProtocolUtil.writeVarInt(buf, entityIds.length);
        for (int i = 0; i < entityIds.length; i++) {
            ProtocolUtil.writeVarInt(buf, entityIds[i]);
        }
    }
}
