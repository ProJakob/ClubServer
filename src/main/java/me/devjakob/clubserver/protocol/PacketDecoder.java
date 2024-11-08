package me.devjakob.clubserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.devjakob.clubserver.Constants;
import me.devjakob.clubserver.except.SilentDecoderException;
import me.devjakob.clubserver.protocol.packet.Direction;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.Packets;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes() < 1) {
			return;
		}

		int length = ProtocolUtil.readVarInt(in);
		if(length > 10)
			System.out.println("Packet length: " + length + " | Readable bytes: " + in.readableBytes());
		if(in.readableBytes() < length) {
			throw new SilentDecoderException("Packet has too few bytes, expected " + length + " bytes but got " + in.readableBytes());
		}

		// This code probably sucks, I really need to learn netty better...
		ByteBuf packetData = in.readSlice(length);
		int packetId = ProtocolUtil.readVarInt(packetData);
		int currentState = ctx.channel().attr(ProtocolAttributes.PROTOCOL_STATE).get();
		Packets packets = Packets.getStateById(currentState);
		if(packets == null)
			throw new SilentDecoderException("Unknown state " + currentState);
		Packet packet = packets.resolvePacket(Direction.SERVERBOUND, packetId);
		if(packet == null)
			throw new SilentDecoderException("Unknown packet 0x" + Integer.toHexString(packetId).toUpperCase() + " in state " + currentState);
		packet.readPacket(packetData);
		out.add(packet);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(cause instanceof SilentDecoderException) {return;}
		Constants.LOG.warn("Exception caught in PacketDecoder", cause);
	}
}
