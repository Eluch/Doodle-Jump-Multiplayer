package me.eluch.libgdx.DoJuMu.game.doodle;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.game.GameObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DoodleBasic extends GameObject {

	protected final String name;
	protected DoodleGenderType character;
	protected boolean facingRight = true;
	protected boolean jumping = true;
	protected boolean alive = true;
	protected final boolean transparent;

	protected final CorePlayer owner;

	protected float maxHeight = 0; //Maximum Y coordinate of doodle

	public DoodleBasic(CorePlayer owner, String name, int startX, int startY, DoodleGenderType genderType, boolean transparent) {
		this.owner = owner;
		this.name = name;
		this.character = genderType;
		rec = new Rectangle(startX, startY, Res._characters.getWidth(), Res._characters.getHeight());
		this.transparent = transparent;
	}

	public void setX(float x) {
		rec.x = x;
	}

	public void setY(float y) {
		rec.y = y;
	}

	public void setXY(float x, float y) {
		setX(x);
		setY(y);
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public float getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(float maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getName() {
		return name;
	}

	public CorePlayer getOwner() {
		return owner;
	}

	public void draw(SpriteBatch batch, Rectangle scrR) {
		if (rec.overlaps(scrR)) {
			if (!(this instanceof DoodleFull)) { // Doodle name display
				Res._doodleNameFont.drawCenter(batch, name, rec.x + Res._characters.getWidth() / 2, Res._characters.getHeight() + rec.y - scrR.y + Res._doodleNameFont.getSize());
			}
			if (facingRight) { // doodle display
				batch.draw((transparent ? Res._characters_t : Res._characters).getSpecificImage(character.ordinal() * 2 + (jumping ? 0 : 1)), rec.x, rec.y - scrR.y);
				if (!alive)
					batch.draw(Res._deadEye.getTexture(), rec.x, rec.y - scrR.y);
			} else {
				batch.draw((transparent ? Res._characters_t : Res._characters).getSpecificImage(character.ordinal() * 2 + (jumping ? 0 : 1)), rec.x + rec.width, rec.y - scrR.y,
						-Res._characters.getWidth(), Res._characters.getHeight());
				if (!alive)
					batch.draw(Res._deadEye.getTexture(), rec.x + rec.width, rec.y - scrR.y, -Res._deadEye.getWidth(), Res._deadEye.getHeight());
			}
		}
	}
}
