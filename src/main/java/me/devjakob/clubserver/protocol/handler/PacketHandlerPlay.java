package me.devjakob.clubserver.protocol.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.Main;
import me.devjakob.clubserver.api.event.PlayerChatEvent;
import me.devjakob.clubserver.protocol.Broadcast;
import me.devjakob.clubserver.protocol.ConnectionInfo;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.play.c2s.C01ChatMessage;
import me.devjakob.clubserver.protocol.packet.play.c2s.C03Player;
import me.devjakob.clubserver.protocol.packet.play.s2c.*;
import me.devjakob.clubserver.util.BlockPos;
import me.devjakob.clubserver.util.Chunk;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketHandlerPlay extends SimpleChannelInboundHandler<Packet> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        ConnectionInfo info = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get();
        if(msg instanceof C01ChatMessage chatMessage) {
            PlayerChatEvent event = new PlayerChatEvent(ctx.channel(), chatMessage.message);
            Constants.EVENTS.post(event);
            Broadcast.broadcastPlay(new S02ChatMessage(event.renderer.render(info.getName(), event.message), (byte) 0));
        } else if(msg instanceof C03Player player) {
            double lastX = info.x;
            double lastY = info.y;
            double lastZ = info.z;
            double delta = 0.0D;
            double deltaX = player.x - lastX;
            double deltaY = player.y - lastY;
            double deltaZ = player.z - lastZ;
            info.x = player.x;
            info.y = player.y;
            info.z = player.z;

            if(player.moving) {
                delta = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
            }

            if(delta > 4.0D) {
                // TODO: Teleported
            } else {
                // TODO: Relative move
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ConnectionInfo info = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get();
        info.assignEntityId();

        ctx.channel().writeAndFlush(new S01JoinGame(
                info.getEntityId(),
                (byte) 1,
                (byte) 0,
                (byte) 0,
                (short) 100,
                "default",
                false
        ));

        String name = info.getName();
        UUID uuid = info.getUuid();

        ctx.channel().writeAndFlush(new S05SpawnPosition(new BlockPos(0, 64, 0)));

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                Chunk.NetworkChunk chunk = Main.world.loadChunk(x, z).toNetwork(true, true, 65535);
                ctx.channel().writeAndFlush(new S21ChunkData(x, z, true, chunk.sectionMask, chunk.data));
            }
        }

        ctx.channel().writeAndFlush(new S08PlayerPositionLook(0, 64, 0, 0, 0));
        Broadcast.broadcastPlay(new S47PlayerListHeaderFooter(Component.text("Current players: " + Main.channels.size()), Component.empty()));
        Broadcast.broadcastPlay(new S02ChatMessage(Component.text(name + " joined the game").color(NamedTextColor.YELLOW), (byte) 0));

        List<S38PlayerListItem.TabEntry> entries = new ArrayList<>();
        List<S0CSpawnPlayer> playerPackets = new ArrayList<>();
        for (Channel channel : Main.channels) {
            if(channel == ctx.channel()) continue;

            ConnectionInfo oInfo = channel.attr(ProtocolAttributes.CONNECTION_INFO).get();
            String oName = oInfo.getName();
            UUID oUuid = oInfo.getUuid();

            entries.add(new S38PlayerListItem.TabEntry(oUuid, oName).setGamemode(1));

            playerPackets.add(new S0CSpawnPlayer(
                    oInfo.getEntityId(),
                    oInfo.getUuid(),
                    0, 5, 0, 0, 0,
                    (short) 0
            ));
        }

        ctx.channel().writeAndFlush(new S38PlayerListItem(S38PlayerListItem.Action.ADD_PLAYER, entries));
        playerPackets.forEach(ctx.channel()::writeAndFlush);

        Broadcast.broadcastPlay(new S38PlayerListItem(S38PlayerListItem.Action.ADD_PLAYER, List.of(
                new S38PlayerListItem.TabEntry(uuid, name).setGamemode(1)
        )));

        Main.channels.writeAndFlush(new S0CSpawnPlayer(
                info.getEntityId(),
                info.getUuid(),
                0, 5, 0, 0, 0,
                (short) 0
        ), ch -> ch != ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ConnectionInfo info = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get();

        String name = info.getName();
        Broadcast.broadcastPlay(new S02ChatMessage(Component.text(name + " left the game").color(NamedTextColor.YELLOW), (byte) 0));

        UUID uuid = info.getUuid();
        Broadcast.broadcastPlay(new S13DestroyEntities(info.getEntityId()));
        Broadcast.broadcastPlay(new S38PlayerListItem(S38PlayerListItem.Action.REMOVE_PLAYER, List.of(new S38PlayerListItem.TabEntry(uuid, name))));
    }
}
