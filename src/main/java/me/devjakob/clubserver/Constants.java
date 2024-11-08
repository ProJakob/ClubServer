package me.devjakob.clubserver;

import com.google.common.eventbus.EventBus;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final JSONComponentSerializer SERIALIZER = GsonComponentSerializer.colorDownsamplingGson();
	public static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static final int PORT = 25565;

	public static final EventBus EVENTS = new EventBus((ex, ctx) -> LOG.error("Event {} caused an exception in {} ({})", ctx.getEvent().getClass().getSimpleName(), ctx.getSubscriber().getClass().getName(), ctx.getSubscriberMethod().getName(), ex));

}
