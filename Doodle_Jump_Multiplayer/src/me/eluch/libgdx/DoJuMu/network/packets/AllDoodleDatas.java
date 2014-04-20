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
			p.writeFloat(x.getDoodle().getMaxHeight());
			p.writeBoolean(x.getDoodle().isFacingRight());
			p.writeBoolean(x.getDoodle().isJumping());
		});

		return p.getByteBuf();
	}

	public static void decode(ReadOnlyPacket iP, CorePlayerContainer<CorePlayer> players) {
		int counter = iP.readInt();
		int id;
		float x;
		float y;
		float maxHeight;
		boolean facingRight;
		boolean jumping;

		for (int i = 0; i < counter; i++) {
			id = iP.readInt();
			x = iP.readFloat();
			y = iP.readFloat();
			maxHeight = iP.readFloat();
			facingRight = iP.readBoolean();
			jumping = iP.readBoolean();
			DoodleDatas dd = new DoodleDatas(x, y, maxHeight, facingRight, jumping);

			CorePlayer p = players.getPlayerByID(id);
			if (players.getMySelf() != p && p.getDoodle().isAlive()) {
				p.getDoodle().setXY(dd.x, dd.y);
				p.getDoodle().setMaxHeight(dd.maxHeight);
				p.getDoodle().setFacingRight(dd.facingRight);
				p.getDoodle().setJumping(dd.jumping);
			}
		}
	}

}
