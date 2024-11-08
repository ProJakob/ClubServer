package me.devjakob.clubserver.protocol.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.status.C01StatusPing;
import me.devjakob.clubserver.protocol.packet.status.S00StatusResponse;
import me.devjakob.clubserver.protocol.packet.status.C00StatusRequest;
import me.devjakob.clubserver.protocol.packet.status.S01StatusPong;

public class PacketHandlerStatus extends SimpleChannelInboundHandler<Packet> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		if(msg instanceof C00StatusRequest) {
			ctx.writeAndFlush(new S00StatusResponse("""
				{
				    "version": {
				        "name": "1.8.x",
				        "protocol": 47
				    },
				    "players": {
				        "max": 100,
				        "online": 1,
				        "sample": [
				            {
				                "name": "ProJakob",
				                "id": "9241fb1e-b4c8-41c8-a548-985b385ea38f"
				            }
				        ]
				    },
				    "description": {
				        "text": "§9§lClub§r§b§lServer§r §7- §3A complete Minecraft Server!\n§7Made during §e§lHack§r§c§lClub§r §d§lHigh§r§5§lSeas"
				    },
				    "favicon": "data:image/png;base64,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"
				}"""));
		}

		if(msg instanceof C01StatusPing ping) {
			ctx.writeAndFlush(new S01StatusPong(ping.payload));
		}
	}
}
