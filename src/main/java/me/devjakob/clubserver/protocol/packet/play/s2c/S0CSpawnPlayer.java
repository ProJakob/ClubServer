package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;
import java.util.UUID;

public class S0CSpawnPlayer implements Packet {

    private final int entityId;
    private final UUID playerUuid;

    private final int x, y, z;
    private final byte yaw, pitch;
    private final short currentItem;

    public S0CSpawnPlayer(int entityId, UUID playerUuid, double x, double y, double z, float yaw, float pitch, short currentItem) {
        this.entityId = entityId;
        this.playerUuid = playerUuid;
        this.x = (int) Math.floor(x * 32.0D);
        this.y = (int) Math.floor(y * 32.0D);
        this.z = (int) Math.floor(z * 32.0D);
        this.yaw = (byte)((int)(yaw * 256.0F / 360.0F));
        this.pitch = (byte)((int)(pitch * 256.0F / 360.0F));
        this.currentItem = currentItem;
    }

    public S0CSpawnPlayer() {
        throw new IllegalCallerException();
    }

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        ProtocolUtil.writeVarInt(buf, entityId);
        buf.writeLong(playerUuid.getMostSignificantBits());
        buf.writeLong(playerUuid.getLeastSignificantBits());
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yaw);
        buf.writeByte(pitch);
        buf.writeShort(currentItem);

        // Health = 20.0f
        buf.writeByte((3 << 5) | 6);
        buf.writeFloat(20.0f);
        buf.writeByte(0x7F);
    }
}
