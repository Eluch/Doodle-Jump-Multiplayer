package me.eluch.libgdx.DoJuMu.game.floors;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class GrayFloor extends Floor {

	public static final int MAX_LIFT = 75;

	private boolean goingUP;
	private float currentLift;
	private float speed;

	public GrayFloor(int x, int y, float speed) {
		super(x, y);
		currentLift = MAX_LIFT - Res.rand.nextInt(MAX_LIFT * 2 + 2);
		this.speed = speed;
	}

	@Override
	protected void onScreen() {
		if (goingUP) {
			currentLift += speed;
			rec.y += speed;
			if (currentLift >= MAX_LIFT) {
				goingUP = false;
			}
		} else {
			currentLift -= speed;
			rec.y -= speed;
			if (currentLift <= -MAX_LIFT) {
				goingUP = true;
			}
		}
	}

	@Override
	protected void doodleHitFloor() {
		effect = Effect.COMMON_JUMP_CAUSER;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._floorSprite.getSpecificImage(3), rec.x, rec.y - scrR.y);
	}

	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.FLOOR);
		p.writeInt(FloorType.GRAY.ordinal());
		p.writeInt((int) rec.x);
		p.writeInt((int) rec.y);
		p.writeFloat(speed);
		return p.getByteBuf();
	}

	public static Floor decode(ReadOnlyPacket p) {
		int x = p.readInt();
		int y = p.readInt();
		float speed = p.readFloat();
		return new GrayFloor(x, y, speed);
	}

}
