package me.devjakob.clubserver.protocol;

import dev.dewy.nbt.Nbt;
import dev.dewy.nbt.tags.collection.CompoundTag;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ReferenceCountUtil;
import me.devjakob.clubserver.except.SilentDecoderException;
import me.devjakob.clubserver.util.ItemStack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProtocolUtil {

    public static final Nbt NBT = new Nbt();

    public static int readVarInt(ByteBuf buf) {
        int value = 0;
        int position = 0;

        while (true) {
            byte b0 = buf.readByte();
            value |= (b0 & 127) << position++ * 7;
            if (position > 5) {
                throw new RuntimeException("VarInt too big");
            }
            if ((b0 & 128) != 128) {
                break;
            }
        }

        return value;
    }

    public static void writeVarInt(ByteBuf buf, int input) {
        while ((input & -128) != 0) {
            buf.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        buf.writeByte(input);
    }

    public static int getVarIntSize(int input) {
        for (int i = 1; i < 5; ++i) {
            if ((input & -1 << i * 7) == 0) {
                return i;
            }
        }

        return 5;
    }

    public static String readString(ByteBuf buf, int maxLength) {
        int length = readVarInt(buf);
        if (length > maxLength * 4) {
            throw new DecoderException("String exceeded max length, max is " + maxLength * 4 + " but got " + length);
        } else if (length < 0) {
            throw new SilentDecoderException("Negative String length received");
        }

        ByteBuf read = buf.readBytes(length);
        String str = read.toString(StandardCharsets.UTF_8);
        ReferenceCountUtil.release(read);

        if (str.length() > maxLength) {
            throw new DecoderException("String exceeded max length, max is " + maxLength + " but got " + length);
        }
        return str;
    }

    public static void writeString(ByteBuf buf, String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

        if (bytes.length > 32767) {
            throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + 32767 + ")");
        }

        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static void writeByteArray(ByteBuf buf, byte[] bytes) {
        writeVarInt(buf, bytes.length);
        buf.writeBytes(bytes);
    }

    public static CompoundTag readNBTCompoundTag(ByteBuf buf) throws IOException {
        int readerIndex = buf.readerIndex();
        byte tagId = buf.readByte();

        if(tagId == 0) {
            return null;
        } else {
            buf.readerIndex(readerIndex);
            try (ByteBufInputStream bbis = new ByteBufInputStream(buf)) {
                return NBT.fromStream(bbis);
            }
        }
    }

    public static ItemStack readItemStack(ByteBuf buf) throws IOException {
        short id = buf.readShort();

        if(id >= 0) {
            byte amount = buf.readByte();
            short meta = buf.readShort();
            CompoundTag nbtData = readNBTCompoundTag(buf);

            ItemStack stack = new ItemStack(id, amount, meta);
            stack.setNbt(nbtData);

            return stack;
        } else {
            return null;
        }
    }

}
