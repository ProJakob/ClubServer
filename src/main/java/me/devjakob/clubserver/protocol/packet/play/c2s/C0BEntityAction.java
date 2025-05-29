package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C0BEntityAction implements Packet {

    public enum Action {

        START_SNEAKING,
        STOP_SNEAKING,
        LEAVE_BED,
        START_SPRINTING,
        STOP_SPRINTING,
        JUMP_WITH_HORSE,
        OPEN_RIDDEN_HORSE_INVENTORY;

        public static final Action[] VALUES = values();

    }

    public int entityId;        // Seems to always be the Entity ID of the player
    public Action action;       // Sneaking Enum
    public int actionParameter; // Only used by Horse Jump

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        entityId = ProtocolUtil.readVarInt(buf);
        int actionId = ProtocolUtil.readVarInt(buf);
        action = Action.values()[actionId];
        actionParameter = ProtocolUtil.readVarInt(buf);
    }

    @Override public void writePacket(ByteBuf buf) throws IOException {}
}
