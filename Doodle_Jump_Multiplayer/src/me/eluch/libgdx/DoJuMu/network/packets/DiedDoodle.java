package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;

public class DiedDoodle {

	private DiedDoodle() {
	}

	public static ByteBuf encode(DoodleDatasEE data) {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.A_PLAYER_DIED);
		op.writeFloat(data.x);
		op.writeFloat(data.y);
		op.writeBoolean(data.facingRight);
		op.writeBoolean(data.jumping);
		op.writeBoolean(data.alive);
		op.writeInt(data.id);

		return op.getByteBuf();
	}

	public static DoodleDatasEE decode(ReadOnlyPacket p) {
		float x = p.readFloat();
		float y = p.readFloat();
		boolean facingRight = p.readBoolean();
		boolean jumping = p.readBoolean();
		boolean alive = p.readBoolean();
		int id = p.readInt();

		return new DoodleDatasEE(x, y, facingRight, jumping, alive, id);
	}

}
