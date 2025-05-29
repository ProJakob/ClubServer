package me.devjakob.clubserver.protocol;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionInfo {

	private static final AtomicInteger playerCount = new AtomicInteger(0);

	private int protocolVersion;
	private String serverAddress;
	private int serverPort;

	private String name;
	private UUID uuid;

	private int entityId;
	public double x, y, z;

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public ConnectionInfo setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
		return this;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public ConnectionInfo setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
		return this;
	}

	public int getServerPort() {
		return serverPort;
	}

	public ConnectionInfo setServerPort(int serverPort) {
		this.serverPort = serverPort;
		return this;
	}

	public String getName() {
		return name;
	}

	public ConnectionInfo setName(String name) {
		this.name = name;
		return this;
	}

	public UUID getUuid() {
		return uuid;
	}

	public ConnectionInfo setUuid(UUID uuid) {
		this.uuid = uuid;
		return this;
	}

	public int getEntityId() {
		return entityId;
	}

	public void assignEntityId() {
		entityId = playerCount.incrementAndGet();
	}
}
