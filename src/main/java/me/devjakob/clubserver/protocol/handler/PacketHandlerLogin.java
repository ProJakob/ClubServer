package me.devjakob.clubserver.protocol.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.login.C00LoginStart;
import me.devjakob.clubserver.protocol.packet.login.S00Disconnect;
import me.devjakob.clubserver.protocol.packet.login.S02LoginSuccess;
import me.devjakob.clubserver.protocol.packet.login.S03SetCompression;

public class PacketHandlerLogin extends SimpleChannelInboundHandler<Packet> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if(msg instanceof C00LoginStart loginStart) {
			ctx.writeAndFlush(new S03SetCompression());
			ctx.writeAndFlush(new S02LoginSuccess(loginStart.name).applyTo(ctx.channel()));
			ctx.channel().attr(ProtocolAttributes.PROTOCOL_STATE).set(0);
			ctx.pipeline().replace(this, "handler", new PacketHandlerPlay());
		}
	}
}
