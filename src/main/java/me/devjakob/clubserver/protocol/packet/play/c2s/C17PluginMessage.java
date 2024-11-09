package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.except.SilentDecoderException;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C17PluginMessage implements Packet {

    public String channel;
    public byte[] data;

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        channel = ProtocolUtil.readString(buf, 20);
        int readableBytes = buf.readableBytes();
        if(readableBytes >= 0 && readableBytes <= 32767) {
            data = new byte[readableBytes];
            buf.readBytes(data);
        } else {
            throw new SilentDecoderException("Payload is too large, max is 32767 bytes");
        }
    }

    @Override public void writePacket(ByteBuf buf) throws IOException {}
}
