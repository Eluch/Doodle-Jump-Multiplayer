package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class YellowFloor extends Floor {

	private int lifeSpan;

	public YellowFloor(int x, int y) {
		super(x, y);
		lifeSpan = 500;
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
		batch.draw(Resources.i._floorSprite.getSpecificImage(2), rec.x, rec.y - scrR.y);
	}

}
