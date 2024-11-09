package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C15ClientSettings implements Packet {

    public String locale;
    public byte viewDistance;
    public byte chatMode;
    public boolean chatColors;
    public short displayedSkinParts;

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        locale = ProtocolUtil.readString(buf, 7);
        viewDistance = buf.readByte();
        chatMode = buf.readByte();
        chatColors = buf.readBoolean();
        displayedSkinParts = buf.readUnsignedByte();
    }

    @Override public void writePacket(ByteBuf buf) throws IOException {}
}
