package me.devjakob.clubserver.protocol.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import me.devjakob.clubserver.protocol.ProtocolUtil;

import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
		msg.markReaderIndex();

		byte[] bytes = new byte[3];

		for (int i = 0; i < bytes.length; ++i) {
			if (!msg.isReadable()) {
				msg.resetReaderIndex();
				return;
			}

			bytes[i] = msg.readByte();

			if (bytes[i] >= 0) {
				ByteBuf packetbuffer = Unpooled.wrappedBuffer(bytes);

				try {
					int packetLength = ProtocolUtil.readVarInt(packetbuffer);

					if (msg.readableBytes() >= packetLength) {
						out.add(msg.readBytes(packetLength));
						return;
					}

					msg.resetReaderIndex();
				} finally {
					packetbuffer.release();
				}
				return;
			}
		}

		throw new CorruptedFrameException("length wider than 21-bit");
	}

}
