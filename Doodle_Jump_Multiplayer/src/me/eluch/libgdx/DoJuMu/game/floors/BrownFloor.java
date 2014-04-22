package me.eluch.libgdx.DoJuMu.game.floors;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class BrownFloor extends Floor {

	public BrownFloor(int x, int y) {
		super(x, y);
	}

	@Override
	protected void onScreen() {
	}

	@Override
	protected void doodleHitFloor() {
		need2Show = false;
		if (Options.isSoundEnabled())
			Res._s_wood_break.play();
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._floorSprite.getSpecificImage(5), rec.x, rec.y - scrR.y);
	}

	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.NEW_FLOOR);
		p.writeInt(FloorType.BROWN.ordinal());
		p.writeInt((int) rec.x);
		p.writeInt((int) rec.y);
		return p.getByteBuf();
	}

	public static Floor decode(ReadOnlyPacket p) {
		int x = p.readInt();
		int y = p.readInt();
		return new BrownFloor(x, y);
	}

}
