package me.eluch.libgdx.DoJuMu.network.packets;

import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleGenderType;
import io.netty.buffer.ByteBuf;

public abstract class OnePlayerConnected {

	public static ByteBuf encode(int id, String name, DoodleGenderType genderType) { // When a player connect to the network
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.ONE_PLAYER_CONNECTED);
		op.writeInt(id);
		op.writeString(name);
		op.writeInt(genderType.ordinal());
		return op.getByteBuf();
	}

	public static CorePlayer decoode(ReadOnlyPacket rp) {
		int id = rp.readInt();
		String name = rp.readString();
		DoodleGenderType genderType = DoodleGenderType.values()[rp.readInt()];
		return new CorePlayer(id, name, genderType);
	}

}
