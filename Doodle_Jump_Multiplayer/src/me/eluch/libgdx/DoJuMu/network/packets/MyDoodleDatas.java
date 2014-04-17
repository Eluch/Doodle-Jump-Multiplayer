package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;

public class MyDoodleDatas {

	private MyDoodleDatas() {
	}

	public static ByteBuf encode(DoodleBasic myDoodle) {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.MY_DOODLE_DATAS);
		op.writeFloat(myDoodle.getRec().x);
		op.writeFloat(myDoodle.getRec().y);
		op.writeBoolean(myDoodle.isFacingRight());
		op.writeBoolean(myDoodle.isJumping());

		return op.getByteBuf();
	}

	public static DoodleDatas decode(ReadOnlyPacket p) {
		float x = p.readFloat();
		float y = p.readFloat();
		boolean facingRight = p.readBoolean();
		boolean jumping = p.readBoolean();

		return new DoodleDatas(x, y, facingRight, jumping);
	}

	public static ByteBuf encodeDied(DoodleBasic myDoodle) {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.I_DIED);
		op.writeFloat(myDoodle.getRec().x);
		op.writeFloat(myDoodle.getRec().y);
		op.writeBoolean(myDoodle.isFacingRight());
		op.writeBoolean(myDoodle.isJumping());
		op.writeBoolean(myDoodle.isAlive());

		return op.getByteBuf();
	}

	public static DoodleDatasE decodeDied(ReadOnlyPacket p) {
		float x = p.readFloat();
		float y = p.readFloat();
		boolean facingRight = p.readBoolean();
		boolean jumping = p.readBoolean();
		boolean alive = p.readBoolean();

		return new DoodleDatasE(x, y, facingRight, jumping, alive);
	}
}
