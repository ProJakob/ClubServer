package me.devjakob.clubserver.protocol.handler;

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
import me.devjakob.clubserver.protocol.packet.play.s2c.S01JoinGame;
import me.devjakob.clubserver.protocol.packet.play.s2c.S02ChatMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PacketHandlerPlay extends SimpleChannelInboundHandler<Packet> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		ConnectionInfo info = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get();
		if(msg instanceof C01ChatMessage chatMessage) {
			PlayerChatEvent event = new PlayerChatEvent(ctx.channel(), chatMessage.message);
			Constants.EVENTS.post(event);
			Broadcast.broadcastPlay(new S02ChatMessage(event.renderer.render(info.getName(), event.message), (byte) 0));
		}
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush(new S01JoinGame(
				0,
				(byte) 3,
				(byte) 0,
				(byte) 0,
				(short) 100,
				"flat",
				false
		));
		String name = ctx.channel().attr(ProtocolAttributes.CONNECTION_INFO).get().getName();
		Broadcast.broadcastPlay(new S02ChatMessage(Component.text(name + " joined the game").color(NamedTextColor.YELLOW), (byte) 0));
	}
}
