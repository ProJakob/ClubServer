package me.devjakob.clubserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.devjakob.clubserver.protocol.packet.Packet;
import me.devjakob.clubserver.protocol.packet.Packets;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
		int id = Packets.getIdByPacket(msg.getClass());
		ProtocolUtil.writeVarInt(out, id);
		msg.writePacket(out);
	}
}
