package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GrayFloor extends Floor {

	public static final int MAX_LIFT = 100;

	private boolean goingUP;
	private float currentLift;
	private float speed;

	public GrayFloor(int x, int y, float speed) {
		super(x, y);
		currentLift = Resources.rand.nextInt(MAX_LIFT + 1);
		this.speed = speed;
	}

	@Override
	protected void onScreen() {
		if (goingUP) {
			currentLift += speed;
			rec.x += speed;
			if (currentLift >= MAX_LIFT) {
				goingUP = false;
			}
		} else {
			currentLift -= speed;
			rec.x -= speed;
			if (currentLift <= MAX_LIFT) {
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
		batch.draw(Resources.i._floorSprite.getSpecificImage(3), rec.x, rec.y - scrR.y);
	}

}
