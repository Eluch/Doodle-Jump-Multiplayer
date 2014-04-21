package me.eluch.libgdx.DoJuMu.game.active_item;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class TrampolineActive extends ActiveItem {

	private static int MAXSPEED = 35;
	private float currentSpeed = MAXSPEED;

	public TrampolineActive(DoodleFull doodle) {
		super(doodle);
		this.overrideable = true;
		Res._s_trampoline.stop();
		Res._s_trampoline.play();
	}

	@Override
	public void update(float delta) {
		if (currentSpeed > 0) {
			currentSpeed -= 0.5;
			if (currentSpeed > 0)
				doodle.setY(doodle.getRec().y + currentSpeed);
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
