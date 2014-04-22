package me.eluch.libgdx.DoJuMu.game.floors;

import io.netty.buffer.ByteBuf;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class BlueFloor extends Floor {

	private boolean travellingRight;
	private float speed;

	public BlueFloor(int x, int y, float speed) {
		super(x, y);
		this.speed = speed;
		travellingRight = new Random().nextBoolean();
	}

	@Override
	protected void onScreen() {
		if (travellingRight) {
			rec.x += speed;
			if (rec.x >= Options.GAME_PLACE_WIDTH - rec.width) {
				travellingRight = false;
			}
		} else {
			rec.x -= speed;
			if (rec.x <= 0) {
				travellingRight = true;
			}
		}
	}

	@Override
	protected void doodleHitFloor() {
		effect = Effect.COMMON_JUMP_CAUSER;
		if (Options.isSoundEnabled())
			Res._s_jump.play();
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._floorSprite.getSpecificImage(1), rec.x, rec.y - scrR.y);
	}

	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.NEW_FLOOR);
		p.writeInt(FloorType.BLUE.ordinal());
		p.writeInt((int) rec.x);
		p.writeInt((int) rec.y);
		p.writeFloat(speed);
		return p.getByteBuf();
	}

	public static Floor decode(ReadOnlyPacket p) {
		int x = p.readInt();
		int y = p.readInt();
		float speed = p.readFloat();
		return new BlueFloor(x, y, speed);
	}

}
