package me.devjakob.clubserver;

import me.devjakob.clubserver.protocol.packet.play.s2c.S00KeepAlive;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KeepAliveTask {

	private static ScheduledExecutorService executor;

	public static void start() {
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(() -> {

			Constants.LOG.debug("Pinged {} clients", Main.channels.size());

			Main.channels.writeAndFlush(new S00KeepAlive());
			//Main.channels.writeAndFlush(new S02ChatMessage());

		}, 0, 15, TimeUnit.SECONDS);
	}

	public static void stop() {
		if(executor != null) {
			executor.shutdown();
		}
	}

}
