package me.eluch.libgdx.DoJuMu.game.floors;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class YellowFloor extends Floor {

	private int lifeSpan;

	public YellowFloor(int x, int y, int lifeSpan) {
		super(x, y);
		this.lifeSpan = lifeSpan;
	}

	@Override
	protected void onScreen() {
		lifeSpan--;
		if (lifeSpan <= 0)
			need2Show = false;
	}

	@Override
	protected void doodleHitFloor() {
		effect = Effect.COMMON_JUMP_CAUSER;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._floorSprite.getSpecificImage(2), rec.x, rec.y - scrR.y);
	}

	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.FLOOR);
		p.writeInt(FloorType.YELLOW.ordinal());
		p.writeInt((int) rec.x);
		p.writeInt((int) rec.y);
		p.writeInt(lifeSpan);
		return p.getByteBuf();
	}

	public static Floor decode(ReadOnlyPacket p) {
		int x = p.readInt();
		int y = p.readInt();
		int lifeSpan = p.readInt();
		return new YellowFloor(x, y, lifeSpan);
	}

}
