package me.eluch.libgdx.DoJuMu.game.active_item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;

public abstract class ActiveItem {

	protected DoodleFull doodle;
	protected boolean overrideable = false; //subclasses may overwrite this
	protected boolean destroy = false; //if should destroy

	public ActiveItem(DoodleFull doodle) {
		this.doodle = doodle;
	}

	public final boolean isOverrideable() {
		return overrideable;
	}

	public final boolean shouldDestroy() {
		return destroy;
	}

	public abstract void update(float delta);

	public abstract void draw(SpriteBatch batch, Rectangle scrR);

}
