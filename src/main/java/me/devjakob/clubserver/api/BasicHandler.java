package me.devjakob.clubserver.api;

import com.google.common.eventbus.Subscribe;
import me.devjakob.clubserver.api.event.PlayerChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BasicHandler {

	@Subscribe
	public void onChat(PlayerChatEvent event) {
		event.renderer = (sender, message) -> {
			return Component.text(sender).color(NamedTextColor.AQUA).append(Component.text(": ").color(NamedTextColor.GRAY).append(Component.text(message).color(NamedTextColor.LIGHT_PURPLE)));
		};
	}

}
