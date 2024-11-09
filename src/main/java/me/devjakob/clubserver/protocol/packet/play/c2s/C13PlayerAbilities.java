package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C13PlayerAbilities implements Packet {

    public byte flags;
    public float flyingSpeed, walkingSpeed;

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        flags = buf.readByte();
        flyingSpeed = buf.readFloat();
        walkingSpeed = buf.readFloat();
    }

    @Override public void writePacket(ByteBuf buf) throws IOException {}
}
