package me.eluch.libgdx.DoJuMu.game.enemy_obj;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.game.GameObject;

public class BlackHole extends GameObject {

	public BlackHole(int x, int y) {
		rec = new Rectangle(x, y, Resources.i._blackhole.getWidth(), Resources.i._blackhole.getHeight());
	}

	public void update(Rectangle doodleRectangle) {
		if (rec.overlaps(doodleRectangle)) {
			effect = Effect.BLACK_HOLE_HIT;
		}
	}

	public void draw(SpriteBatch batch, Rectangle scrR) {
		if (rec.overlaps(scrR)) {
			batch.draw(Resources.i._blackhole.getTexture(), rec.x, rec.y - scrR.y);
		}
	}

}
