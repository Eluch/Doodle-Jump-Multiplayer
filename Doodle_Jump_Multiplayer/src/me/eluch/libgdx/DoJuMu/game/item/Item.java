package me.eluch.libgdx.DoJuMu.game.item;

import me.eluch.libgdx.DoJuMu.game.GameObject;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item extends GameObject { //Not equipped, just on the game ground
	
	protected static final int OFFSET = 7;

	protected final Floor bindedFloor;

	public Item(Floor bindedFloor) {
		this.bindedFloor = bindedFloor;
		this.rec = new Rectangle(bindedFloor.getRec());
	}

	/**
	 * For updating the Item's position
	 */
	public abstract void updatePos();

	public final void update(Rectangle scrR, Rectangle doodleRectangle, Rectangle doodleFootRectangle, boolean doodleFalling) {
		if (scrR.overlaps(rec) && need2Show) {
			if (rec.overlaps(doodleRectangle))
				itemHitDoodle();
			if (rec.overlaps(doodleFootRectangle) && doodleFalling)
				itemHitDoodleFoot();
		}
	}

	protected abstract void itemHitDoodle();

	protected abstract void itemHitDoodleFoot();

	public final void draw(SpriteBatch batch, Rectangle scrR) {
		if (rec.overlaps(scrR) && need2Show)
			render(batch, scrR);

	}

	protected abstract void render(SpriteBatch batch, Rectangle scrR);

}
