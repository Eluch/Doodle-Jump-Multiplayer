package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class WhiteFloor extends Floor {

	public WhiteFloor(int x, int y) {
		super(x, y);
	}

	@Override
	protected void onScreen() {
	}

	@Override
	protected void doodleHitFloor() {
		effect = Effect.COMMON_JUMP_CAUSER;
		need2Show = false;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._floorSprite.getSpecificImage(4), rec.x, rec.y - scrR.y);
	}

}
