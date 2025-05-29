package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.util.ItemStack;

import java.io.IOException;

public class C10CreativeInventoryAction implements Packet {

    public short slot;
    public ItemStack clickedItem;

    @Override
    public void readPacket(ByteBuf buf) throws IOException {
        slot = buf.readShort();
        clickedItem = ProtocolUtil.readItemStack(buf);

        System.out.println(slot + ": " + clickedItem);
    }

    @Override public void writePacket(ByteBuf buf) throws IOException {}
}
