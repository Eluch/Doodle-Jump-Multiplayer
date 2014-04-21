package me.eluch.libgdx.DoJuMu.game.active_item;

import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class SpringActive extends ActiveItem {

	private static int MAXSPEED = 29;
	private float currentSpeed = MAXSPEED;

	public SpringActive(DoodleFull doodle) {
		super(doodle);
	}

	@Override
	public void update(float delta) {
		if (currentSpeed > 0) {
			currentSpeed -= 0.5;
			if (currentSpeed > 0)
				doodle.getRec().y += currentSpeed;
		} else {
			doodle.setJumping(false);
			doodle.setvSpeed(0);
			this.destroy = true;
		}
	}

	@Override
	public void draw(SpriteBatch batch, Rectangle scrR) {
	}

}
