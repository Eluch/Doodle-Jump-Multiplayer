package me.eluch.libgdx.DoJuMu.game.active_item;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class PropellerHatActive extends ActiveItem {

	private static final int SHIFT_X = 2;
	private static final int SHIFT_Y = 6;

	private static final int LIFT = 5000;
	private float currentLift = 0f;

	private static final int MAXSPEED = 25;
	private float currentSpeed;

	public PropellerHatActive(DoodleFull doodle) {
		super(doodle);
		doodle.setvSpeed(0);
		this.currentSpeed = doodle.getvSpeed();
		doodle.setJumping(true);
		if (Options.isSoundEnabled())
			Res._s_propeller.play();
	}

	@Override
	public void update(float delta) {
		if (currentLift < LIFT) {
			currentSpeed += 0.5f;
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
			batch.draw(Res._propellerhat.getNextImage(), doodle.getRec().x - SHIFT_X, doodle.getRec().y - scrR.y + Res._characters.getHeight() - SHIFT_Y);
		else
			batch.draw(Res._propellerhat.getNextImage(), doodle.getRec().x + 12, doodle.getRec().y - scrR.y + Res._characters.getHeight() - SHIFT_Y);
	}

}
