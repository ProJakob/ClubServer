package me.devjakob.clubserver.protocol.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.Util;
import me.devjakob.clubserver.api.event.PlayerChatEvent;
import me.devjakob.clubserver.protocol.Broadcast;
import me.devjakob.clubserver.protocol.ConnectionInfo;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.play.c2s.C01ChatMessage;
import me.devjakob.clubserver.protocol.packet.play.s2c.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class PacketHandlerPlay extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        ConnectionInfo info = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get();
        if(msg instanceof C01ChatMessage chatMessage) {
            PlayerChatEvent event = new PlayerChatEvent(ctx.channel(), chatMessage.message);
            Constants.EVENTS.post(event);
            Broadcast.broadcastPlay(new S02ChatMessage(event.renderer.render(info.getName(), event.message), (byte) 0));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ctx.channel().writeAndFlush(new S01JoinGame(
                0,
                (byte) 1,
                (byte) 0,
                (byte) 0,
                (short) 100,
                "flat",
                false
        ));
        ctx.channel().writeAndFlush(new S08PlayerPositionLook(0, 64, 0, 0, 0));
        ctx.channel().writeAndFlush(new S47PlayerListHeaderFooter(Component.text("Hi :3"), Component.empty()));
        String name = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get().getName();
        Broadcast.broadcastPlay(new S02ChatMessage(Component.text(name + " joined the game").color(NamedTextColor.YELLOW), (byte) 0));

        List<S21ChunkData> data = new ArrayList<>();
        for (int i = -10; i <= 10; i++) {
            for (int j = -10; j <= 10; j++) {
                data.add(Util.prepareFlatPacket(i, j));
            }
        }
        S26MapChunkBulk bulk = new S26MapChunkBulk(true, data.toArray(S21ChunkData[]::new));

        // Does not seem to have any noticable effect..?
        ctx.channel().writeAndFlush(bulk);

        // Client seems to acknowledge chunks existing when sending this but not the Bulk packet? Help required, im out of Ideas.
        for (S21ChunkData datum : data) {
            ctx.channel().writeAndFlush(datum);
        }
    }
}
