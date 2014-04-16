package me.eluch.libgdx.DoJuMu.game.doodle;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.GameObject;
import me.eluch.libgdx.DoJuMu.gfx.AnimatedImage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public final class Doodle extends GameObject {

	private static final AnimatedImage DOODLE_IMAGE = Res._characters;

	private int id;
	private DoodleGenderType character;
	private Rectangle doodleLegRect;
	private boolean facingRight = true;
	private boolean jumping = true;
	private boolean alive = true;

	public Doodle(int id, int startX, int startY, DoodleGenderType genderType) {
		this.id = id;
		this.character = genderType;
		rec = new Rectangle(startX, startY, DOODLE_IMAGE.getWidth(), DOODLE_IMAGE.getHeight());
		doodleLegRect = new Rectangle(startX, startY, DOODLE_IMAGE.getWidth(), 5);
	}

	public Rectangle getFootRect() {
		return doodleLegRect;
	}

	public Rectangle getDoodleRect() {
		return rec;
	}

	public void setX(int x) {
		rec.x = doodleLegRect.x = x;
	}

	public void setY(int y) {
		rec.y = doodleLegRect.y = y;
	}

	public void setXY(int x, int y) {
		setX(x);
		setY(y);
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

	public void draw(SpriteBatch batch, Rectangle scrR) {
		if (facingRight) {
			batch.draw(DOODLE_IMAGE.getSpecificImage(character.ordinal() * 2 + (jumping ? 0 : 1)), rec.x, rec.y);
		} else {
			batch.draw(DOODLE_IMAGE.getSpecificImage(character.ordinal() * 2 + (jumping ? 0 : 1)), rec.x + rec.width, rec.y, -DOODLE_IMAGE.getWidth(), DOODLE_IMAGE.getHeight());
		}
	}
}
