package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C16ClientStatus implements Packet {

    public enum Action {

        PERFORM_RESPAWN,
        REQUEST_STATS,
        TAKING_INVENTORY_ACHIEVEMENT;

        public static final Action[] VALUES = values();

    }

    public Action action;

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        int actionId = ProtocolUtil.readVarInt(buf);
        this.action = Action.VALUES[actionId];
    }

    @Override
    public void writePacket(ByteBuf buf) throws IOException {

    }

}
