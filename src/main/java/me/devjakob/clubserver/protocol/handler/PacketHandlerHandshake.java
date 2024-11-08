package me.devjakob.clubserver.protocol.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.devjakob.clubserver.Main;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.handshaking.C00Handshake;
import me.devjakob.clubserver.protocol.packet.login.S00Disconnect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class PacketHandlerHandshake extends SimpleChannelInboundHandler<Packet> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if(msg instanceof C00Handshake handshake) {
			if(handshake.nextState == 1) {
				ctx.pipeline().replace(this, "handler", new PacketHandlerStatus());
			} else if(handshake.nextState == 2) {
				// Prevent clients that aren't 1.8.x from joining.
				if(handshake.protocolVersion != 47) {
					// Figure out a better solution later.
					ctx.writeAndFlush(new S00Disconnect(Component.text("This server only supports 1.8.x").color(NamedTextColor.RED))).addListener(ChannelFutureListener.CLOSE);
					return;
				}

				ctx.pipeline().replace(this, "handler", new PacketHandlerLogin());
			} else {
				ctx.close();
				return;
			}

			ctx.channel().attr(ProtocolAttributes.PROTOCOL_STATE).set(handshake.nextState);
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Main.channels.add(ctx.channel());
	}
}
