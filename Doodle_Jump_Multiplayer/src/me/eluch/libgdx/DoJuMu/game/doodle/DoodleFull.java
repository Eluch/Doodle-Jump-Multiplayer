package me.eluch.libgdx.DoJuMu.game.doodle;

import me.eluch.libgdx.DoJuMu.Options.ScreenRes;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.Res;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;

public final class DoodleFull extends DoodleBasic {

	private static final float H_MAXSPEED = 15f;
	private static final float H_ACCELERATION = 1.1f;
	private static final float H_SLOWING = 1.15f;
	private static final float H_FORCESLOWING = 1.35f;

	private static final float V_MAXSPEED = 25f;
	private static final float V_ACCELERATION = 1.09f; //When falling
	private static final float V_SLOWING = 1.09f; // When jumping
	private static final float V_MAX_FALLSPEED = 15f;

	private Rectangle doodleLegRect;
	private float hSpeed = 0; // horizontalSpeed
	private float vSpeed = V_MAXSPEED;

	public DoodleFull(CorePlayer owner, String name, int startX, int startY, DoodleGenderType genderType, boolean transparent) {
		super(owner, name, startX, startY, genderType, transparent);
		doodleLegRect = new Rectangle(startX, startY, Res._characters.getWidth(), 10);
	}

	public Rectangle getFootRect() {
		return doodleLegRect;
	}

	@Override
	public void setX(float x) {
		rec.x = doodleLegRect.x = x;
	}

	@Override
	public void setY(float y) {
		rec.y = doodleLegRect.y = y;
		if (rec.y > maxHeight)
			maxHeight = rec.y;
	}

	private void goLeft() {
		if (hSpeed == 0)
			hSpeed -= 1.5f;
		else if (hSpeed <= -1.5f)
			hSpeed *= H_ACCELERATION;
		else if (hSpeed > 0 && hSpeed < 1.5f)
			hSpeed = 0;
		else if (hSpeed > 0)
			hSpeed /= H_FORCESLOWING;

		if (hSpeed < -H_MAXSPEED)
			hSpeed = -H_MAXSPEED;
	}

	private void goRight() {
		if (hSpeed == 0)
			hSpeed += 1.5f;
		else if (hSpeed >= 1.5f)
			hSpeed *= H_ACCELERATION;
		else if (hSpeed < 0 && hSpeed > -1.5f)
			hSpeed = 0;
		else if (hSpeed < 0)
			hSpeed /= H_FORCESLOWING;

		if (hSpeed > H_MAXSPEED)
			hSpeed = H_MAXSPEED;
	}

	private void slowDown() {
		if (hSpeed == 0)
			return;
		hSpeed /= H_SLOWING;
		if (Math.abs(hSpeed) <= 1)
			hSpeed = 0;
	}

	private void jump() {
		if (vSpeed <= 0) {
			vSpeed = 0;
			jumping = false;
		} else if (vSpeed < 1)
			vSpeed = 0;
		else
			vSpeed /= V_SLOWING;
	}

	private void fall() {
		if (vSpeed == 0)
			vSpeed = -1;
		else
			vSpeed *= V_ACCELERATION;

		if (vSpeed < -V_MAX_FALLSPEED)
			vSpeed = -V_MAX_FALLSPEED;
	}

	public void doJump() {
		jumping = true;
		vSpeed = V_MAXSPEED;
	}

	public void update(float delta) {
		if (alive) {
			//Vertically moving
			if (jumping)
				jump();
			else
				fall();
			setY(rec.y + vSpeed);

			//Horizontally moving
			if (Gdx.input.isKeyPressed(Keys.A))
				goLeft();
			if (Gdx.input.isKeyPressed(Keys.D))
				goRight();
			if (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))
				slowDown();
			setX(rec.x + hSpeed);

			// If doodle is on the edge of the screen:
			if (rec.x <= -Res._characters.getWidth())
				setX(ScreenRes.width / 2);
			else if (rec.x >= ScreenRes.width / 2)
				setX(-Res._characters.getWidth());

			if (hSpeed < 0)
				facingRight = false;
			else if (hSpeed > 0)
				facingRight = true;
		}
	}
}
