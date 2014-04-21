package me.eluch.libgdx.DoJuMu.game.active_item;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class SpringShoeActive extends ActiveItem {

	private static int MAXJUMPCOUNT = 5;
	private int currentJumpCount = 0;
	private boolean jumpingWithShoe = false;

	private static int MAXSPEED = 29;
	private float currentSpeed = MAXSPEED;

	public SpringShoeActive(DoodleFull doodle) {
		super(doodle);
	}

	@Override
	public void update(float delta) {
		if (doodle.isJumping()) { //jumping
			if (!jumpingWithShoe) {
				jumpingWithShoe = true;
				currentJumpCount++;
				currentSpeed = MAXSPEED;
			}
			if (currentSpeed > 0) {
				currentSpeed -= 0.5;
				if (currentSpeed > 0)
					doodle.setY(doodle.getRec().y + currentSpeed);
			} else {
				doodle.setJumping(false);
				doodle.setvSpeed(0);
				jumpingWithShoe = false;
			}
		} else { //falling
			doodle.verticalMove();
		}

		if (!jumpingWithShoe && currentJumpCount >= MAXJUMPCOUNT) {
			doodle.setvSpeed(0);
			this.destroy = true;
		}
	}

	@Override
	public void draw(SpriteBatch batch, Rectangle scrR) {
		batch.draw(Res._springshoe.getSpecificImage(doodle.isJumping() ? 1 : 0), doodle.getRec().x + (doodle.isFacingRight() ? 0 : 5),
				doodle.getRec().y - scrR.y - Res._springshoe.getHeight() + 6);
	}

}
