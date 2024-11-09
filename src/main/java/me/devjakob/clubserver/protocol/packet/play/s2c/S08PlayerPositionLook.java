package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class S08PlayerPositionLook implements Packet {

    public enum Flags {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        Flags(int value) {
           this.value = value;
        }

        private final int value;

        public static int apply(Flags[] flags) {
            int i = 0;

            for (Flags flag : flags) {
                i |= 1 << flag.value;
            }

            return i;
        }
    }

    private final double x, y, z;
    private final float yaw, pitch;
    private final int flags;

    public S08PlayerPositionLook(double x, double y, double z, float yaw, float pitch, Flags... flags) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = Flags.apply(flags);
    }

    public S08PlayerPositionLook() {
        throw new IllegalStateException("Don't call this!");
    }

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeByte(flags);
    }
}
