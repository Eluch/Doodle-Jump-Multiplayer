package me.eluch.libgdx.DoJuMu.game.floors;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.game.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BlueFloor extends Floor {

	private boolean travellingRight;
	private float speed;

	public BlueFloor(int x, int y) {
		super(x, y);
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
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Resources.i._floorSprite.getSpecificImage(1), rec.x, rec.y - scrR.y);
	}

}
