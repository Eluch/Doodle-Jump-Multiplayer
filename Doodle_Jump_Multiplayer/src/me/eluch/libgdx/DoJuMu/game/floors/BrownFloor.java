package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Resources;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BrownFloor extends Floor {

	public BrownFloor(int x, int y) {
		super(x, y);
	}

	@Override
	protected void onScreen() {
	}

	@Override
	protected void doodleHitFloor() {
		need2Show = false;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Resources.i._floorSprite.getSpecificImage(5), rec.x, rec.y - scrR.y);
	}

}
