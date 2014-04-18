package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class YellowFloor extends Floor {

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

}
