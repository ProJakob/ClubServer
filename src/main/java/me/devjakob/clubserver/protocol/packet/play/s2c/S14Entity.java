package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S14Entity implements Packet {

    protected final int entityId;
    protected byte x, y, z, yaw, pitch;
    protected boolean onGround;
    protected boolean rotating;

    public S14Entity(int entityId) {
        this.entityId = entityId;
    }

    public S14Entity() {
        throw new IllegalCallerException();
    }

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
    }

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        ProtocolUtil.writeVarInt(buf, entityId);
    }

    public static class S15EntityRelativeMove extends S14Entity {

        public S15EntityRelativeMove(int entityId, byte x, byte y, byte z, boolean onGround) {
            super(entityId);
            this.x = x;
            this.y = y;
            this.z = z;
            this.onGround = onGround;
        }

        @Override
        public void writePacket(ByteBuf buf) throws IOException {
            super.writePacket(buf);
            buf.writeByte(x);
            buf.writeByte(y);
            buf.writeByte(z);
            buf.writeBoolean(onGround);
        }
    }

    public static class S16EntityLook extends S14Entity {

        public S16EntityLook(int entityId, byte yaw, byte pitch, boolean onGround) {
            super(entityId);
            this.yaw = yaw;
            this.pitch = pitch;
            this.rotating = true;
            this.onGround = onGround;
        }

        @Override
        public void writePacket(ByteBuf buf) throws IOException {
            super.writePacket(buf);
            buf.writeByte(yaw);
            buf.writeByte(pitch);
            buf.writeBoolean(onGround);
        }
    }

    public static class S17EntityLookMove extends S14Entity {

        public S17EntityLookMove(int entityId, byte x, byte y, byte z, byte yaw, byte pitch, boolean onGround) {
            super(entityId);
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.rotating = true;
        }

        @Override
        public void writePacket(ByteBuf buf) throws IOException {
            super.writePacket(buf);
            buf.writeByte(x);
            buf.writeByte(y);
            buf.writeByte(z);
            buf.writeByte(yaw);
            buf.writeByte(pitch);
            buf.writeBoolean(onGround);
        }
    }
}
