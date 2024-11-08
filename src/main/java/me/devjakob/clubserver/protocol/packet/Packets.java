package me.devjakob.clubserver.protocol.packet;

import me.devjakob.clubserver.protocol.packet.handshaking.C00Handshake;
import me.devjakob.clubserver.protocol.packet.login.C00LoginStart;
import me.devjakob.clubserver.protocol.packet.login.S00Disconnect;
import me.devjakob.clubserver.protocol.packet.login.S02LoginSuccess;
import me.devjakob.clubserver.protocol.packet.login.S03SetCompression;
import me.devjakob.clubserver.protocol.packet.play.c2s.*;
import me.devjakob.clubserver.protocol.packet.play.s2c.*;
import me.devjakob.clubserver.protocol.packet.status.C01StatusPing;
import me.devjakob.clubserver.protocol.packet.status.S00StatusResponse;
import me.devjakob.clubserver.protocol.packet.status.C00StatusRequest;
import me.devjakob.clubserver.protocol.packet.status.S01StatusPong;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public enum Packets {

	HANDSHAKING(-1) {
		{
			addPacket(Direction.SERVERBOUND, 0x00, C00Handshake.class);
		}
	},
	PLAY(0) {
		{
			addPacket(Direction.CLIENTBOUND, 0x00, S00KeepAlive.class);
			addPacket(Direction.CLIENTBOUND, 0x01, S01JoinGame.class);
			addPacket(Direction.CLIENTBOUND, 0x02, S02ChatMessage.class);
			addPacket(Direction.CLIENTBOUND, 0x40, S40Disconnect.class);
			addPacket(Direction.CLIENTBOUND, 0x47, S47PlayerListHeaderFooter.class);

			addPacket(Direction.SERVERBOUND, 0x00, C00KeepAlive.class);
			addPacket(Direction.SERVERBOUND, 0x01, C01ChatMessage.class);
			addPacket(Direction.SERVERBOUND, 0x03, C03Player.class);
			addPacket(Direction.SERVERBOUND, 0x04, C04PlayerPosition.class);
			addPacket(Direction.SERVERBOUND, 0x05, C05PlayerLook.class);
			addPacket(Direction.SERVERBOUND, 0x06, C06PlayerPositionLook.class);
		}
	},
	STATUS(1) {
		{
			addPacket(Direction.SERVERBOUND, 0x00, C00StatusRequest.class);
			addPacket(Direction.SERVERBOUND, 0x01, C01StatusPing.class);

			addPacket(Direction.CLIENTBOUND, 0x00, S00StatusResponse.class);
			addPacket(Direction.CLIENTBOUND, 0x01, S01StatusPong.class);
		}
	},
	LOGIN(2) {
		{
			addPacket(Direction.SERVERBOUND, 0x00, C00LoginStart.class);

			addPacket(Direction.CLIENTBOUND, 0x00, S00Disconnect.class);
			addPacket(Direction.CLIENTBOUND, 0x02, S02LoginSuccess.class);
			addPacket(Direction.CLIENTBOUND, 0x03, S03SetCompression.class);
		}
	};

	public final int stateId;

	Packets(int stateId) {
		this.stateId = stateId;
	}

	// Why am I storing the constructor? Simple: We don't need the class here and doing the constructor lookup once on boot is better.

	private final Map<Direction, Map<Integer, Constructor<? extends Packet>>> packetRegistry = new HashMap<>();

	void addPacket(Direction direction, int id, Class<? extends Packet> clazz) {
		try {
			packetRegistry
					.computeIfAbsent(direction, k -> new HashMap<>())
					.put(id, clazz.getConstructor());

			// Register to the lookup in case the packet can't be registered
			if(packetIdLookup == null)
				packetIdLookup = new HashMap<>();
			packetIdLookup.put(clazz, id);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public Packet resolvePacket(Direction direction, int id) throws ReflectiveOperationException {
		Map<Integer, Constructor<? extends Packet>> directionMap = packetRegistry.get(direction);
		if (directionMap != null) {
			Constructor<? extends Packet> packetConstructor = directionMap.get(id);
			if (packetConstructor != null) {
				return packetConstructor.newInstance();
			}
		}
		return null;
	}

	// :>:>:>:>:>:>:>:>:>:>:>:>:>:>
	//    Static Field n Methods
	// :>:>:>:>:>:>:>:>:>:>:>:>:>:>

	private static HashMap<Class<? extends Packet>, Integer> packetIdLookup;

	public static int getIdByPacket(Class<? extends Packet> clazz) {
		return packetIdLookup.get(clazz);
	}

	public static final Packets[] VALUES = new Packets[] { HANDSHAKING, PLAY, STATUS, LOGIN };

	public static Packets getStateById(int stateId) {
		for (int i = 0; i < VALUES.length; i++) {
			Packets packet = VALUES[i];
			if (packet.stateId == stateId) {
				return packet;
			}
		}
		return null;
	}
}
