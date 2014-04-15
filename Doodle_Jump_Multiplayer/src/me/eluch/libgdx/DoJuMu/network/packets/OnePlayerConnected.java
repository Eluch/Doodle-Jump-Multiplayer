package me.eluch.libgdx.DoJuMu.network.packets;

import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import io.netty.buffer.ByteBuf;

public abstract class OnePlayerConnected {

	public static ByteBuf encode(int id, String name) { // When a player connect to the network
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.ONE_PLAYER_CONNECTED);
		op.writeInt(id);
		op.writeString(name);
		return op.getByteBuf();
	}

	public static CorePlayer decoode(ReadOnlyPacket rp) {
		int id = rp.readInt();
		String name = rp.readString();
		return new CorePlayer(id, name);
	}

}
