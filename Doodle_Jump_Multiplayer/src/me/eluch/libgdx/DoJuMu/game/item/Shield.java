package me.eluch.libgdx.DoJuMu.game.item;

import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.gfx.LoadedImage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Shield extends Item {

	LoadedImage texture;

	public Shield(Floor bindedFloor) {
		super(bindedFloor);
		texture = Resources.i._protectionbubbleitem;
	}

	@Override
	public void updatePos() {
		this.rec.x = bindedFloor.getRec().x + (bindedFloor.getRec().width - this.texture.getWidth()) / 2;
		this.rec.y = bindedFloor.getRec().y + texture.getHeight() + OFFSET;
	}

	@Override
	protected void itemHitDoodle() {
		need2Show = false;
		effect = Effect.SHIELD_EQUIPPING;
	}

	@Override
	protected void itemHitDoodleFoot() {
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(texture.getTexture(), rec.x, rec.y - scrR.y);

	}

}
