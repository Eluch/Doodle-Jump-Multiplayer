package me.eluch.libgdx.DoJuMu.network.packets;

import java.util.ArrayList;

import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import io.netty.buffer.ByteBuf;

public class AllDoodleDatas {

	private AllDoodleDatas() {
	}

	public static ByteBuf encode(ArrayList<ServerPlayer> players) {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.ALL_DOODLE_DATAS);
		p.writeInt(players.size());
		players.forEach(x -> {
			p.writeInt(x.getId());
			p.writeFloat(x.getDoodle().getRec().x);
			p.writeFloat(x.getDoodle().getRec().y);
			p.writeBoolean(x.getDoodle().isFacingRight());
			p.writeBoolean(x.getDoodle().isJumping());
		});

		return p.getByteBuf();
	}

	public static void decode(ReadOnlyPacket iPacket, CorePlayerContainer<CorePlayer> players) {
		int counter = iPacket.readInt();
		int id;
		float x;
		float y;
		boolean facingRight;
		boolean jumping;

		for (int i = 0; i < counter; i++) {
			id = iPacket.readInt();
			x = iPacket.readFloat();
			y = iPacket.readFloat();
			facingRight = iPacket.readBoolean();
			jumping = iPacket.readBoolean();
			DoodleDatas dd = new DoodleDatas(x, y, facingRight, jumping);

			CorePlayer p = players.getPlayerByID(id);
			if (players.getMySelf() != p && p.getDoodle().isAlive()) {
				p.getDoodle().setXY(dd.x, dd.y);
				p.getDoodle().setFacingRight(dd.facingRight);
				p.getDoodle().setJumping(dd.jumping);
			}
		}
	}

}
