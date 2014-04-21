package me.eluch.libgdx.DoJuMu.game.score;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Options.ScreenRes;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;
import me.eluch.libgdx.DoJuMu.gfx.LoadedFont;

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
		LoadedFont font;
		if (isMyself)
			if (doodle.isAlive())
				font = Res._scoreFontBoldB;
			else
				font = Res._scoreFontBoldR;
		else if (doodle.isAlive())
			font = Res._scoreFontB;
		else
			font = Res._scoreFontR;

		int ping = doodle.getOwner().getPing();

		if (!isMyself) {
			batch.draw(Res._posArrow_large.getSpecificImage(higher ? 0 : 1), Options.GAME_PLACE_WIDTH + 20, y);
		}
		(ping == -1 || ping > 100 ? Res._pingFontR : ping > 30 ? Res._pingFontY : Res._pingFontG).drawLeft(batch, "ping: " + doodle.getOwner().getPing() + " ms",
				Options.GAME_PLACE_WIDTH + 55, y + 22);
		font.drawLeft(batch, doodle.getName(), Options.GAME_PLACE_WIDTH + 50, y);
		font.drawRight(batch, Integer.toString((int) doodle.getMaxHeight()), ScreenRes.width - 10, y);
	}
}
