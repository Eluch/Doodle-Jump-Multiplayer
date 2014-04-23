package me.eluch.libgdx.DoJuMu.game;

import java.util.ArrayList;
import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.game.active_item.JetpackActive;
import me.eluch.libgdx.DoJuMu.game.active_item.PropellerHatActive;
import me.eluch.libgdx.DoJuMu.game.active_item.SpringActive;
import me.eluch.libgdx.DoJuMu.game.active_item.SpringShoeActive;
import me.eluch.libgdx.DoJuMu.game.active_item.TrampolineActive;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleFull;
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

	private final ArrayList<DoodleBasic> doodles = new ArrayList<>();
	//private final ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	//private final ArrayList<BlackHole> blackHoles = new ArrayList<>();
	private final ArrayList<Floor> floors = new ArrayList<>();
	private final ArrayList<Item> items = new ArrayList<>();

	private DoodleFull myDoodle;
	private final Rectangle scrR = new Rectangle(-100, 0, Options.GAME_PLACE_WIDTH + 200, Options.GAME_PLACE_HEIGHT); // Screen Rectangle
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
			if (floor.rec.y < getCleanupY() || !floor.need2Show) {
				if (del == null)
					del = new ArrayList<>();
				del.add(floor);
			}
		}
		if (del != null)
			floors.removeAll(del);
	}

	private void itemCleanup() {
		ArrayList<Item> del = null;
		for (Item item : items) {
			if (item.rec.y < getCleanupY() || !item.need2Show) {
				if (del == null)
					del = new ArrayList<>();
				del.add(item);
			}
		}
		if (del != null)
			items.removeAll(del);
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

	private float getHighestAliveDoodleY() {
		float y = -1;
		for (DoodleBasic doodle : doodles) {
			if (doodle.getRec().y > y && doodle.isAlive())
				y = doodle.getRec().y;
		}
		return y;
	}

	private float getLowestAliveDoodleY() {
		float y = -1;
		boolean found = false;
		for (DoodleBasic doodle : doodles) {
			if (!found && doodle.isAlive()) {
				y = doodle.getRec().y;
				found = true;
			}
			if (doodle.getRec().y < y && doodle.isAlive())
				y = doodle.getRec().y;
		}
		return y;
	}

	private float getCleanupY() {
		float y = getLowestAliveDoodleY();
		if (y == -1 || (y != -1 && y > scrR.y - 100))
			y = scrR.y - 100;
		else
			y -= 200;
		return y;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public Rectangle getScrR() {
		return scrR;
	}

	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Keys.R) && !rPressed) {
			rPressed = true;
			running = !running;
		} else if (!Gdx.input.isKeyPressed(Keys.R) && rPressed)
			rPressed = false;

		if (running || !Options.DEBUG) { // RUNNING PART
			floors.forEach(x -> {
				x.update(scrR, myDoodle.getFootRect(), !myDoodle.isJumping());
				if (x.effect == Effect.COMMON_JUMP_CAUSER) {
					myDoodle.doJump();
					x.effect = Effect.NOTHING;
				}
			});
			items.forEach(x -> {
				x.update(scrR, myDoodle.getRec(), myDoodle.getFootRect(), !myDoodle.isJumping());
				if (x.getEffect() == Effect.SHIELD_EQUIPPING)
					myDoodle.setShielded(true);
				if (x.getEffect() != Effect.NOTHING) {
					switch (x.getEffect()) {
					case JETPACK_EQUIPPING:
						myDoodle.setActiveItem(new JetpackActive(myDoodle));
						break;
					case PROPELLER_HAT_EQUIPPING:
						myDoodle.setActiveItem(new PropellerHatActive(myDoodle));
						break;
					case SPRING_JUMP:
						myDoodle.setActiveItem(new SpringActive(myDoodle));
						break;
					case SPRING_SHOE_EQUIPPING:
						myDoodle.setActiveItem(new SpringShoeActive(myDoodle));
						break;
					case TRAMPOLINE_JUMP:
						myDoodle.setActiveItem(new TrampolineActive(myDoodle));
						break;
					default:
						break;
					}
					x.resetEffect();
				}
			});
			itemCleanup();
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
					if (scrR.y - 100 > y && y != -1)
						scrR.y = y;
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
		floors.forEach(x -> x.draw(batch, scrR));
		items.forEach(x -> x.draw(batch, scrR));
		myDoodle.draw(batch, scrR);
		doodles.forEach(x -> x.draw(batch, scrR));
	}

}
