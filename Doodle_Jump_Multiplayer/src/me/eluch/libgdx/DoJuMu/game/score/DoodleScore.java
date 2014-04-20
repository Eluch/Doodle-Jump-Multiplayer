package me.eluch.libgdx.DoJuMu.game.score;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Options.ScreenRes;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

public final class DoodleScore {

	private final DoodleBasic doodle;
	private final boolean isMyself;

	private int y = ScreenRes.height;
	private int placement = 0;
	private boolean higher = false;

	public DoodleScore(DoodleBasic doodle) {
		this.doodle = doodle;
		isMyself = (doodle instanceof DoodleFull);
	}

	public boolean isHigher() {
		return higher;
	}

	public void setHigher(boolean higher) {
		this.higher = higher;
	}

	public DoodleBasic getDoodle() {
		return doodle;
	}

	public void setPlacement(int placement) {
		this.placement = placement;
	}

	public void updatePosY() {
		int ty = Options.GAME_PLACE_HEIGHT - 30 - (int) Res._clearpattern.getHeight() * (placement - 1); // target y

		if (ty > y) {
			y += 5;
			if (ty < y)
				y = ty;
		} else if (ty < y) {
			y -= 5;
			if (ty > y)
				y = ty;
		}
	}

	public void draw(SpriteBatch batch) {
		if (!isMyself) {
			batch.draw(Res._posArrow_large.getSpecificImage(higher ? 0 : 1), Options.GAME_PLACE_WIDTH + 20, y);
		}
		(isMyself ? Res._scoreFontBold : Res._scoreFont).drawLeft(batch, doodle.getName(), Options.GAME_PLACE_WIDTH + 50, y);
		(isMyself ? Res._scoreFontBold : Res._scoreFont).drawRight(batch, Integer.toString((int) doodle.getMaxHeight()), ScreenRes.width - 10, y);
	}
}
