package me.devjakob.clubserver.protocol;

import java.util.UUID;

public class ConnectionInfo {

	private int protocolVersion;
	private String serverAddress;
	private int serverPort;

	private String name;
	private UUID uuid;

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
}
