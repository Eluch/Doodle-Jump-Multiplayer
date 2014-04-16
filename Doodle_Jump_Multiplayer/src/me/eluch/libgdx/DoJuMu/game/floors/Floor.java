package me.eluch.libgdx.DoJuMu.game.floors;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.GameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Floor extends GameObject {
	
	public Floor(int x, int y) {
		rec = new Rectangle(x, y, Res._floorSprite.getWidth(), Res._floorSprite.getHeight());
	}

	public final void update(Rectangle scrR, Rectangle doodleFootRectangle, boolean doodleFalling) {
		if (rec.overlaps(scrR) && need2Show) {

			if (doodleFalling) {
				if (rec.overlaps(doodleFootRectangle))
					doodleHitFloor();
			}
		}
	}

	protected abstract void onScreen();

	protected abstract void doodleHitFloor();

	public final void draw(SpriteBatch batch, Rectangle scrR) {
		if (rec.overlaps(scrR) && need2Show)
			render(batch, scrR);

	}

	protected abstract void render(SpriteBatch batch, Rectangle scrR);

}
