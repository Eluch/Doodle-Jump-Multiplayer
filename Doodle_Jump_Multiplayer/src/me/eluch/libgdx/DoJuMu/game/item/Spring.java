package me.eluch.libgdx.DoJuMu.game.item;

import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.gfx.AnimatedImage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Spring extends Item {

	boolean touched = false;
	AnimatedImage texture;

	public Spring(Floor bindedFloor) {
		super(bindedFloor);
		this.texture = Resources.i._spring;
		this.rec = new Rectangle(bindedFloor.getRec().x, bindedFloor.getRec().y, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void updatePos() {
		this.rec.x = bindedFloor.getRec().x + (bindedFloor.getRec().width - this.texture.getWidth()) / 2;
		this.rec.y = bindedFloor.getRec().y + texture.getHeight() + OFFSET;
	}

	@Override
	protected void itemHitDoodle() {
	}

	@Override
	protected void itemHitDoodleFoot() {
		touched = true;
		effect = Effect.SPRING_JUMP;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(texture.getSpecificImage(touched ? 1 : 0).getTexture(), rec.x, rec.y - scrR.y);
	}

}
