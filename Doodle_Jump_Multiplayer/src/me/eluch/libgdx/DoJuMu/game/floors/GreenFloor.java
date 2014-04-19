package me.eluch.libgdx.DoJuMu.game.floors;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class GreenFloor extends Floor {

	public GreenFloor(int x, int y) {
		super(x, y);
	}

	@Override
	protected void onScreen() {
	}

	@Override
	protected void doodleHitFloor() {
		effect = Effect.COMMON_JUMP_CAUSER;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._floorSprite.getSpecificImage(0), rec.x, rec.y - scrR.y);
	}

	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.FLOOR);
		p.writeInt(FloorType.GREEN.ordinal());
		p.writeInt((int) rec.x);
		p.writeInt((int) rec.y);
		return p.getByteBuf();
	}

	public static Floor decode(ReadOnlyPacket p) {
		int x = p.readInt();
		int y = p.readInt();
		return new GreenFloor(x, y);
	}

}
