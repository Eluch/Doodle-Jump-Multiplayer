package me.eluch.libgdx.DoJuMu.game.score;

import java.util.ArrayList;
import java.util.Comparator;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class ScoreHandler {

	private final ArrayList<DoodleScore> scores = new ArrayList<>();

	private static final int SHIFT = 20;

	private DoodleBasic frontDoodle = null;
	private DoodleBasic behindDoodle = null;
	private float myDoodleMaxHeight = 0;

	public ScoreHandler(DoodleFull myDoodle, ArrayList<DoodleBasic> doodles) {
		scores.add(new DoodleScore(myDoodle));
		doodles.forEach(x -> scores.add(new DoodleScore(x)));
	}

	public boolean isEveryOneDied() {
		for (DoodleScore score : scores) {
			if (score.getDoodle().isAlive())
				return false;
		}
		return true;
	}

	public String getWinnerName() {
		return scores.get(0).getDoodle().getName();
	}

	public void update() { //make the order
		scores.sort(new Comparator<DoodleScore>() {
			@Override
			public int compare(DoodleScore arg0, DoodleScore arg1) {
				return Float.compare(arg1.getDoodle().getMaxHeight(), arg0.getDoodle().getMaxHeight());
			}
		});

		int myPlacement = 0;

		int placement = 0;
		boolean higher = true;
		for (DoodleScore dScore : scores) {
			dScore.setPlacement(++placement);
			if (dScore.getDoodle() instanceof DoodleFull) {
				higher = false;
				myPlacement = dScore.getPlacement();
				myDoodleMaxHeight = dScore.getDoodle().getMaxHeight();
			}
			dScore.setHigher(higher);
			dScore.updatePosY();
		}

		frontDoodle = behindDoodle = null;

		if (myPlacement == 1 && scores.size() > 1) {
			behindDoodle = scores.get(1).getDoodle();
		} else if (myPlacement == scores.size() && scores.size() > 1) {
			frontDoodle = scores.get(myPlacement - 2).getDoodle();
		} else if (scores.size() >= 3) {
			frontDoodle = scores.get(myPlacement - 2).getDoodle();
			behindDoodle = scores.get(myPlacement).getDoodle();
		}
	}

	public void draw(SpriteBatch batch, Rectangle scrR) {
		scores.forEach(x -> x.draw(batch));
		if (frontDoodle != null) {
			batch.draw(Res._posArrow_small.getSpecificImage(0), Options.GAME_PLACE_WIDTH / 2 - Res._posArrow_small.getWidth() / 2, Options.GAME_PLACE_HEIGHT - SHIFT);
			Res._frontDoodleFont.drawRight(batch, frontDoodle.getName(), Options.GAME_PLACE_WIDTH / 2 - Res._posArrow_small.getWidth(), Options.GAME_PLACE_HEIGHT - SHIFT);
			Res._frontDoodleFont.drawLeft(batch, Integer.toString((int) (frontDoodle.getMaxHeight() - myDoodleMaxHeight)),
					Options.GAME_PLACE_WIDTH / 2 + Res._posArrow_small.getWidth(), Options.GAME_PLACE_HEIGHT - SHIFT);
		}
		if (behindDoodle != null) {
			batch.draw(Res._posArrow_small.getSpecificImage(1), Options.GAME_PLACE_WIDTH / 2 - Res._posArrow_small.getWidth() / 2, SHIFT);
			Res._behindDoodleFont.drawRight(batch, behindDoodle.getName(), Options.GAME_PLACE_WIDTH / 2 - Res._posArrow_small.getWidth(), SHIFT);
			Res._behindDoodleFont.drawLeft(batch, Integer.toString((int) (myDoodleMaxHeight - behindDoodle.getMaxHeight())),
					Options.GAME_PLACE_WIDTH / 2 + Res._posArrow_small.getWidth(), SHIFT);
		}
	}
}
