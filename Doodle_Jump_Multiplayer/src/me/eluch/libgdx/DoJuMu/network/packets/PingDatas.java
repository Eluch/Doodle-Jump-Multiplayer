package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.data.ServerPlayerContainer;

public abstract class PingDatas {

	public static ByteBuf encode(ServerPlayerContainer players) {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.PING_DATAS);
		op.writeInt(players.getNumberOfPlayers());
		for (ServerPlayer player : players.getPlayers()) {
			op.writeInt(player.getId());
			op.writeLong(player.getPing());
		}
		return op.getByteBuf();
	}

	public static void decode(ReadOnlyPacket packet, CorePlayerContainer<CorePlayer> players) {
		int numOfPings = packet.readInt();
		int id;
		long ping;
		CorePlayer player;
		for (int i = 0; i < numOfPings; i++) {
			id = packet.readInt();
			ping = packet.readLong();
			player = players.getPlayerByID(id);
			if (player != null) {
				player.setPing((int) ping);
			}
		}
	}

}
