package me.eluch.libgdx.DoJuMu.game.active_item;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class JetpackActive extends ActiveItem {

	private static final int SHIFT_Y = 10;

	private static final int LIFT = 15000;
	private float currentLift = 0f;

	private static final int MAXSPEED = 35;
	private float currentSpeed;

	public JetpackActive(DoodleFull doodle) {
		super(doodle);
		doodle.setvSpeed(0);
		this.currentSpeed = doodle.getvSpeed();
		doodle.setJumping(true);
		Res._s_rocket.play();
	}

	@Override
	public void update(float delta) {
		if (currentLift < LIFT) {
			currentSpeed += 1f;
			if (currentSpeed > MAXSPEED)
				currentSpeed = MAXSPEED;
			currentLift += currentSpeed;
			doodle.setY(doodle.getRec().y + currentSpeed);
		} else {
			if (currentSpeed > 0) {
				currentSpeed -= 0.3f;
				doodle.setY(doodle.getRec().y + currentSpeed);
			} else {
				doodle.setvSpeed(0);
				doodle.setJumping(false);
				this.destroy = true;
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, Rectangle scrR) {
		if (doodle.isFacingRight())
			batch.draw(Res._jetpackhalf.getNextImage(), doodle.getRec().x - Res._jetpackhalf.getWidth(), doodle.getRec().y - scrR.y - SHIFT_Y);
		else
			batch.draw(Res._jetpackhalf.getNextImage(), doodle.getRec().x + doodle.getRec().width + Res._jetpackhalf.getWidth(), doodle.getRec().y - scrR.y - SHIFT_Y,
					-Res._jetpackhalf.getWidth(), Res._jetpackhalf.getHeight());
	}

}
