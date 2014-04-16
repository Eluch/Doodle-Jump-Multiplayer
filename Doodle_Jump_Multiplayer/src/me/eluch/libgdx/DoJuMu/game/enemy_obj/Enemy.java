package me.eluch.libgdx.DoJuMu.game.enemy_obj;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.game.GameObject;
import me.eluch.libgdx.DoJuMu.gfx.LoadedImage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy extends GameObject {

	private LoadedImage texture;

	private boolean headingRight;
	private final int speed;

	public Enemy(EnemyType enemyType, int xPos, int yPos, int speed) {
		pharseType(enemyType);
		this.rec = new Rectangle(xPos, yPos, texture.getWidth(),
				texture.getHeight());
		headingRight = new Random().nextBoolean();
		this.speed = speed;
	}

	private void pharseType(EnemyType type) {
		switch (type) {
		case FATTY:
			texture = Res._mob1;
			break;
		case THREE_EYE:
			texture = Res._mob2;
			break;
		case FLYING_VAMPIRE:
			texture = Res._mob3;
			break;
		}
	}

	public final void update(Rectangle scrR, Rectangle doodleRectangle,
			boolean doodleProtected) {
		if (rec.overlaps(scrR) && need2Show) {
			// on-screen
			if (headingRight) {
				rec.x += speed;
				if (rec.x > Options.GAME_PLACE_WIDTH - rec.height)
					headingRight = false;
			} else { // headingLeft
				rec.x -= speed;
				if (rec.x < 0)
					headingRight = true;
			}

			if (rec.overlaps(doodleRectangle)) {
				// hit
				if (doodleProtected)
					need2Show = false;
				effect = Effect.MONSTER_HIT;
			}
		}
	}

	public final void draw(SpriteBatch batch, Rectangle scrR) {
		if (rec.overlaps(scrR) && need2Show) {
			batch.draw(texture.getTexture(), rec.x, rec.y - scrR.y);
		}
	}

}
