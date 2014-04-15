package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;

public class OnePlayerDisconnected {
	
	public static ByteBuf encode(int id) { // When a player disconnect
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.ONE_PLAYER_DISCONNECTED);
		op.writeInt(id);
		return op.getByteBuf();
	}

	public static int decoode(ReadOnlyPacket rp) {
		int id = rp.readInt();
		return id;
	}

}
