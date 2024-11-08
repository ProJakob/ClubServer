package me.devjakob.clubserver.api.event;

import io.netty.channel.Channel;
import me.devjakob.clubserver.api.ChatRenderer;

public class PlayerChatEvent {

	public final Channel channel;
	public String message;
	public ChatRenderer renderer = ChatRenderer.DEFAULT;

	public PlayerChatEvent(Channel channel, String message) {
		this.channel = channel;
		this.message = message;
	}
}
