package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S18EntityTeleport implements Packet {

    private final int entityId;
    private final int x, y, z;
    private final byte yaw, pitch;
    private final boolean onGround;

    public S18EntityTeleport(int entityId, double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.entityId = entityId;
        this.x = (int) Math.floor(x * 32.0D);
        this.y = (int) Math.floor(y * 32.0D);
        this.z = (int) Math.floor(z * 32.0D);
        this.yaw = (byte)((int)(yaw * 256.0F / 360.0F));
        this.pitch = (byte)((int)(pitch * 256.0F / 360.0F));
        this.onGround = onGround;
    }

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        ProtocolUtil.writeVarInt(buf, entityId);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(yaw);
        buf.writeByte(pitch);
        buf.writeBoolean(onGround);
    }
}
