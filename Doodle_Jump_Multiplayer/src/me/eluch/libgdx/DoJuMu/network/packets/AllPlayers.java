package me.eluch.libgdx.DoJuMu.network.packets;

import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.data.ServerPlayerContainer;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleGenderType;
import io.netty.buffer.ByteBuf;

public abstract class AllPlayers {

	public static ByteBuf encode(ServerPlayerContainer players) {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.RESPONSE_ALL_PLAYERS);
		op.writeInt(players.getNumberOfPlayers());
		for (ServerPlayer player : players.getPlayers()) {
			op.writeInt(player.getId());
			op.writeString(player.getName());
			op.writeInt(player.getGenderType().ordinal());
		}
		return op.getByteBuf();
	}

	public static void decode(ReadOnlyPacket p, CorePlayerContainer<CorePlayer> players) {
		int counter = p.readInt();
		int id;
		String name;
		DoodleGenderType genderType;
		for (int i = 0; i < counter; i++) {
			id = p.readInt();
			name = p.readString();
			genderType = DoodleGenderType.values()[p.readInt()];
			
			CorePlayer player = new CorePlayer(id, name, genderType);
			if (!players.isMyself(player)) {
				players.addPlayer(player);
			}
		}
	}

}
