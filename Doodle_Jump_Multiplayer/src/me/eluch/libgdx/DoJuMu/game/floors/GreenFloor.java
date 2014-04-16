package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GreenFloor extends Floor {

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

}
