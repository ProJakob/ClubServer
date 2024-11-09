package me.devjakob.clubserver.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.except.SilentDecoderException;
import me.devjakob.clubserver.protocol.ProtocolAttributes;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Direction;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.Packets;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		if (msg.readableBytes() != 0) {
			int i = ProtocolUtil.readVarInt(msg);
			ctx.channel().attr(ProtocolAttributes.PROTOCOL_STATE).setIfAbsent(-1);
			int currentState = ctx.channel().attr(ProtocolAttributes.PROTOCOL_STATE).get();
			Packets packets = Packets.getStateById(currentState);

			if(packets == null) {
				msg.skipBytes(msg.readableBytes());
				throw new SilentDecoderException("Unknown protocol state: " + currentState);
			}

			Packet packet = packets.resolvePacket(Direction.SERVERBOUND, i);

			if (packet == null) {
				msg.skipBytes(msg.readableBytes());
				throw new SilentDecoderException("Bad packet id 0x" + Integer.toHexString(i).toUpperCase());
			} else {
				packet.readPacket(msg);

				if (msg.readableBytes() > 0) {
					throw new SilentDecoderException("Packet " + currentState + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + msg.readableBytes() + " bytes extra whilst reading packet 0x" + Integer.toHexString(i).toUpperCase());
				} else {
					out.add(packet);
				}
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Constants.LOG.warn("Exception caught in PacketDecoder", cause);
	}
}
