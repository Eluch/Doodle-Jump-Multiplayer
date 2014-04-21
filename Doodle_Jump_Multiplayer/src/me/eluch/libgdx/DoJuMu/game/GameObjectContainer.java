package me.eluch.libgdx.DoJuMu.game;

import java.util.ArrayList;
import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.BlackHole;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.Enemy;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.game.floors.GreenFloor;
import me.eluch.libgdx.DoJuMu.game.item.Item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameObjectContainer {

	//Just for degug
	private boolean running = false;
	private boolean rPressed = false;

	private ArrayList<DoodleBasic> doodles = new ArrayList<>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<BlackHole> blackHoles = new ArrayList<>();
	private ArrayList<Floor> floors = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();

	private DoodleFull myDoodle;
	private Rectangle scrR = new Rectangle(-100, 0, Options.GAME_PLACE_WIDTH + 200, Options.GAME_PLACE_HEIGHT); // Screen Rectangle
	private float afterDeadDelta = 0;

	public GameObjectContainer(CorePlayerContainer<?> playerC) { // CONSTRUCTOR

		DoodleFull myDoodle = new DoodleFull(playerC.getMySelf(), playerC.getMySelf().getName(), new Random().nextInt(Options.ScreenRes.width / 2 - Res._characters.getWidth()),
				30, Options.getCharacter(), false);
		this.myDoodle = myDoodle;

		playerC.getMySelf().setDoodle(myDoodle);

		for (CorePlayer p : playerC.getPlayers()) {
			if (p.getDoodle() == null) {
				p.setDoodle(new DoodleBasic(p, p.getName(), 10, 30, p.getGenderType(), true));
				doodles.add(p.getDoodle());
			}
		}

		floors.add(new GreenFloor(0, 10));
		floors.add(new GreenFloor(80, 10));
		floors.add(new GreenFloor(164, 10));
		floors.add(new GreenFloor(246, 10));
		floors.add(new GreenFloor(328, 10));
		floors.add(new GreenFloor(410, 10));

		for (Floor floor : floors) {
			if (floor.effect == Effect.COMMON_JUMP_CAUSER)
				myDoodle.setJumping(true);
		}
	}

	public DoodleFull getMyDoodle() {
		return myDoodle;
	}

	private void floorCleanup() {
		ArrayList<Floor> del = null;
		for (Floor floor : floors) {
			if (floor.rec.y < scrR.y - 100 || !floor.need2Show) {
				if (del == null)
					del = new ArrayList<>();
				del.add(floor);
			}
		}
		if (del != null)
			floors.removeAll(del);
	}

	public int getPatternSliding() {
		return (int) (scrR.y % Res._pattern.getHeight());
	}

	public ArrayList<Floor> getFloors() {
		return floors;
	}

	public ArrayList<DoodleBasic> getDoodles() {
		return doodles;
	}

	public float getHighestAliveDoodleY() {
		float y = -1;
		for (DoodleBasic doodle : doodles) {
			if (doodle.getRec().y > y && doodle.isAlive())
				y = doodle.getRec().y;
		}
		return y;
	}

	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Keys.R) && !rPressed) {
			rPressed = true;
			running = !running;
		} else if (!Gdx.input.isKeyPressed(Keys.R) && rPressed)
			rPressed = false;

		if (running || !Options.DEBUG) {
			floors.forEach(x -> {
				x.update(scrR, myDoodle.getFootRect(), !myDoodle.isJumping());
				if (x.effect == Effect.COMMON_JUMP_CAUSER) {
					myDoodle.doJump();
					x.effect = Effect.NOTHING;
				}
			});
			for (Floor floor : floors) {
				floor.update(scrR, myDoodle.getFootRect(), !myDoodle.isJumping());
			}
			myDoodle.update(delta);

			if (myDoodle.isAlive() && myDoodle.rec.y > (scrR.y + Options.GAME_PLACE_HEIGHT / 2)) { // scrR update
				scrR.y = myDoodle.rec.y - Options.GAME_PLACE_HEIGHT / 2;
			} else if (!myDoodle.isAlive()) {
				if (afterDeadDelta < 2.5f)
					afterDeadDelta += delta;
				else {
					float y = getHighestAliveDoodleY();
					if (y != -1)
						if (y > (scrR.y + Options.GAME_PLACE_HEIGHT / 2)) { // scrR update to highest alive player after dead
							scrR.y = y - Options.GAME_PLACE_HEIGHT / 2;
						}
				}
			}
			if (myDoodle.rec.y < scrR.y) {
				myDoodle.setAlive(false);
				myDoodle.setXY(myDoodle.getRec().x, myDoodle.getMaxHeight());
			}
			floorCleanup();
		}
	}

	public void render(SpriteBatch batch) {
		floors.forEach(x -> {
			x.draw(batch, scrR);
		});
		myDoodle.draw(batch, scrR);
		doodles.forEach(x -> x.draw(batch, scrR));
	}

}
