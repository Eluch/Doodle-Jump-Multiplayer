package me.eluch.libgdx.DoJuMu.game.score;

import java.util.ArrayList;
import java.util.Comparator;

import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class ScoreHandler {

	private final ArrayList<DoodleScore> scores = new ArrayList<>();

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

		int placement = 0;
		boolean higher = true;
		for (DoodleScore dScore : scores) {
			dScore.setPlacement(++placement);
			if (dScore.getDoodle() instanceof DoodleFull)
				higher = false;
			dScore.setHigher(higher);
			dScore.updatePosY();
		}
	}

	public void draw(SpriteBatch batch) {
		scores.forEach(x -> x.draw(batch));
	}
}
