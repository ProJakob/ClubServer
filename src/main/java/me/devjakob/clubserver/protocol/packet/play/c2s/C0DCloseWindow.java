package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C0DCloseWindow implements Packet {

    public short windowId;

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        windowId = buf.readUnsignedByte();
    }

    @Override public void writePacket(ByteBuf buf) throws IOException {}
}
