package me.devjakob.clubserver.protocol.packet.play.c2s;

import io.netty.buffer.ByteBuf;
import me.devjakob.clubserver.protocol.packet.Packet;

import java.io.IOException;

public class C03Player implements Packet {

	public double x, y, z;
	public float yaw, pitch;
	public boolean onGround;
	public boolean moving, rotating;

	public C03Player(boolean onGround) {
		this.onGround = onGround;
	}

	public C03Player() {}

	@Override
	public void readPacket(ByteBuf buf) throws IOException {
		onGround = buf.readBoolean();
	}

	@Override public void writePacket(ByteBuf buf) throws IOException {}

	public static class C04PlayerPosition extends C03Player {

		public C04PlayerPosition(double posX, double posY, double posZ, boolean isOnGround) {
			this.x = posX;
			this.y = posY;
			this.z = posZ;
			this.onGround = isOnGround;
			this.moving = true;
		}


		@Override
		public void readPacket(ByteBuf buf) throws IOException {
			this.x = buf.readDouble();
			this.y = buf.readDouble();
			this.z = buf.readDouble();
			super.readPacket(buf);
		}
	}

	public static class C05PlayerLook extends C03Player {

		public C05PlayerLook(float yaw, float pitch) {
			this.yaw = yaw;
			this.pitch = pitch;
			this.onGround = true;
			this.rotating = true;
		}

		@Override
		public void readPacket(ByteBuf buf) throws IOException {
			this.yaw = buf.readFloat();
			this.pitch = buf.readFloat();
			super.readPacket(buf);
		}
	}

	public static class C06PlayerPositionLook extends C03Player {

		public C06PlayerPositionLook(double posX, double posY, double posZ, float yaw, float pitch, boolean onGround) {
			this.x = posX;
			this.y = posY;
			this.z = posZ;
			this.yaw = yaw;
			this.pitch = pitch;
			this.onGround = onGround;
			this.rotating = true;
			this.moving = true;
		}

	}
}
