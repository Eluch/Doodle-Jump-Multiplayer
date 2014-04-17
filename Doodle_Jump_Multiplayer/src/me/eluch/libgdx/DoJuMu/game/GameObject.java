package me.eluch.libgdx.DoJuMu.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {

	protected Rectangle rec;
	protected boolean need2Show;
	protected Effect effect;

	public GameObject() {
		need2Show = true;
		effect = Effect.NOTHING;
	}

	public final void setRec(Rectangle rectangle) {
		this.rec = rectangle;
	}

	public final boolean getNeed2Show() {
		return need2Show;
	}

	public final Rectangle getRec() {
		return rec;
	}

	public Effect getEffect() {
		return effect;
	}
}
