package me.devjakob.clubserver.api;

import net.kyori.adventure.text.Component;

public interface ChatRenderer {

	public static final ChatRenderer DEFAULT = (sender, message) -> {
		return Component.text(sender).append(Component.text(": ").append(Component.text(message)));
	};

	Component render(String sender, String message);

}
