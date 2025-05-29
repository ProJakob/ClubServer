package me.devjakob.clubserver.protocol.packet.play.s2c;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.ProtocolUtil;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class S38PlayerListItem implements Packet {

    public S38PlayerListItem(Action action, List<TabEntry> entries) {
        this.action = action;
        this.entries = entries;
    }

    public S38PlayerListItem() {
        throw new IllegalCallerException();
    }

    public enum Action {

        ADD_PLAYER(0),
        UPDATE_GAMEMODE(1),
        UPDATE_LATENCY(2),
        UPDATE_DISPLAY_NAME(3),
        REMOVE_PLAYER(4);

        public final int action;

        Action(int action) {
            this.action = action;
        }
    }

    public static class TabEntry {

        public final UUID uuid;
        public final String name;
        public int gamemode;
        public int ping;
        public String displayName;

        public TabEntry(UUID uuid, String name) {
            this.uuid = uuid;
            this.name = name;
        }

        public TabEntry setGamemode(int gamemode) {
            this.gamemode = gamemode;
            return this;
        }

        public TabEntry setPing(int ping) {
            this.ping = ping;
            return this;
        }

        public TabEntry setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }
    }

    private final Action action;
    private final List<TabEntry> entries;

    @Override public void readPacket(ByteBuf buf) throws IOException {}

    @Override
    public void writePacket(ByteBuf buf) throws IOException {
        ProtocolUtil.writeVarInt(buf, action.action);
        ProtocolUtil.writeVarInt(buf, entries.size());

        for (TabEntry entry : entries) {
            switch (action) {
                case ADD_PLAYER -> {
                    buf.writeLong(entry.uuid.getMostSignificantBits());
                    buf.writeLong(entry.uuid.getLeastSignificantBits());

                    ProtocolUtil.writeString(buf, entry.name);

                    // No properties
                    ProtocolUtil.writeVarInt(buf, 0);

                    ProtocolUtil.writeVarInt(buf, entry.gamemode);
                    ProtocolUtil.writeVarInt(buf, entry.ping);

                    buf.writeBoolean(entry.displayName != null);
                    if(entry.displayName != null) {
                        ProtocolUtil.writeString(buf, entry.displayName);
                    }
                }
                case UPDATE_GAMEMODE -> {
                    buf.writeLong(entry.uuid.getMostSignificantBits());
                    buf.writeLong(entry.uuid.getLeastSignificantBits());

                    ProtocolUtil.writeVarInt(buf, entry.gamemode);
                }

                case UPDATE_LATENCY -> {
                    buf.writeLong(entry.uuid.getMostSignificantBits());
                    buf.writeLong(entry.uuid.getLeastSignificantBits());

                    ProtocolUtil.writeVarInt(buf, entry.ping);
                }
                case UPDATE_DISPLAY_NAME -> {
                    buf.writeLong(entry.uuid.getMostSignificantBits());
                    buf.writeLong(entry.uuid.getLeastSignificantBits());

                    buf.writeBoolean(entry.displayName != null);
                    if(entry.displayName != null) {
                        ProtocolUtil.writeString(buf, entry.displayName);
                    }
                }
                case REMOVE_PLAYER -> {
                    buf.writeLong(entry.uuid.getMostSignificantBits());
                    buf.writeLong(entry.uuid.getLeastSignificantBits());
                }
            }
        }
    }
}
