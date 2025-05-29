package me.devjakob.clubserver.api;

import com.google.common.eventbus.Subscribe;
import io.netty.channel.Channel;
import me.devjakob.clubserver.Main;
import me.devjakob.clubserver.api.event.PlayerChatEvent;
import me.devjakob.clubserver.protocol.packet.play.s2c.S02ChatMessage;
import me.devjakob.clubserver.protocol.packet.play.s2c.S21ChunkData;
import me.devjakob.clubserver.protocol.packet.play.s2c.S23PacketBlockChange;
import me.devjakob.clubserver.util.BlockPos;
import me.devjakob.clubserver.util.Chunk;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BasicHandler {

	@Subscribe
	public void onChat(PlayerChatEvent event) {
		if(event.message.equalsIgnoreCase("/resend")) {
			Chunk.NetworkChunk chunk = Main.world.loadChunk(0, 0).toNetwork(true, true, 65535);
			event.channel.writeAndFlush(new S21ChunkData(0, 0, true, chunk.sectionMask, chunk.data));
		} else if(event.message.equalsIgnoreCase("/unload")) {
			event.channel.writeAndFlush(new S21ChunkData(0, 0, true, 0, new byte[0]));
		} else if(event.message.startsWith("/fillchunk ")) {
			try {
				String[] split = event.message.substring("/fillchunk ".length()).split(" ");

				int chunkX = Integer.parseInt(split[0]);
				int chunkZ = Integer.parseInt(split[1]);
				int blockId = Integer.parseInt(split[2]);
				int metadata = Integer.parseInt(split[3]);

				Chunk chunk = Main.world.loadChunk(chunkX, chunkZ);

				for (int i = 0; i < 16; i++) {
					for (int j = 0; j < 16; j++) {
						chunk.set(i, 0, j, (char) (blockId << 4 | metadata));
					}
				}

				Chunk.NetworkChunk nChunk = chunk.toNetwork(true, true, 65535);
				for (Channel channel : Main.channels) {
					channel.writeAndFlush(new S21ChunkData(chunkX, chunkZ, true, 0, new byte[0]));
					channel.writeAndFlush(new S21ChunkData(chunkX, chunkZ, true, nChunk.sectionMask, nChunk.data));
				}
			} catch (Exception e) {
				event.channel.writeAndFlush(new S02ChatMessage(Component.text("/fillchunk <x> <z> <bId> <meta>"), (byte) 0));
			}
		} else if(event.message.startsWith("/setblock ")) {
			try {
				String[] split = event.message.substring("/setblock ".length()).split(" ");

				int x = Integer.parseInt(split[0]);
				int y = Integer.parseInt(split[1]);
				int z = Integer.parseInt(split[2]);
				int blockId = Integer.parseInt(split[3]);
				int metadata = Integer.parseInt(split[4]);
				char state = (char) (blockId << 4 | metadata);

				Main.world.set(x, y, z, state);
				for (Channel channel : Main.channels) {
					channel.writeAndFlush(new S23PacketBlockChange(new BlockPos(x, y, z), state));
				}
			} catch (Exception e) {
				event.channel.writeAndFlush(new S02ChatMessage(Component.text("/setblock <x> <y> <z> <bId> <meta>"), (byte) 0));
			}
		}

		event.renderer = (sender, message) -> Component.text(sender, NamedTextColor.AQUA)
				.append(Component.text(": ", NamedTextColor.GRAY))
				.append(Component.text(message, NamedTextColor.LIGHT_PURPLE));
	}

}
