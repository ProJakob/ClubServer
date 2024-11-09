package me.devjakob.clubserver.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import me.devjakob.clubserver.protocol.ProtocolUtil;

public class VarIntLengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
		int len = msg.readableBytes();
		int varIntSize = ProtocolUtil.getVarIntSize(len);
		if(varIntSize > 3) {
			throw new EncoderException("Can't fit " + varIntSize + " bytes (max is 3)");
		}

		out.ensureWritable(varIntSize + len);
		ProtocolUtil.writeVarInt(out, len);
		out.writeBytes(msg, msg.readerIndex(), len);
	}
}
