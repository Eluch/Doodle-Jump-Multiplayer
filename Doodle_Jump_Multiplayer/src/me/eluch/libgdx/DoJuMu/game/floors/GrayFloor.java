package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GrayFloor extends Floor {

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

}
