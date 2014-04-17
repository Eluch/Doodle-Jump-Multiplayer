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

	protected ArrayList<DoodleBasic> doodles = new ArrayList<>();
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected ArrayList<BlackHole> blackHoles = new ArrayList<>();
	protected ArrayList<Floor> floors = new ArrayList<>();
	protected ArrayList<Item> items = new ArrayList<>();

	protected DoodleFull myDoodle;
	protected Rectangle scrR = new Rectangle(0, 0, Options.ScreenRes.width / 2, Options.ScreenRes.height); // Screen Rectangle

	public GameObjectContainer(CorePlayerContainer<?> playerC) { // CONSTRUCTOR

		DoodleFull myDoodle = new DoodleFull(playerC.getMySelf().getName(), new Random().nextInt(Options.ScreenRes.width / 2 - Res._characters.getWidth()), 30,
				Options.getCharacter(), false);
		this.myDoodle = myDoodle;

		playerC.getMySelf().setDoodle(myDoodle);

		for (CorePlayer p : playerC.getPlayers()) {
			if (p.getDoodle() == null) {
				p.setDoodle(new DoodleBasic(p.getName(), 10, 30, p.getGenderType(), true));
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

	public void update(float delta) {
		if (Gdx.input.isKeyPressed(Keys.R) && !rPressed) {
			rPressed = true;
			running = !running;
		} else if (!Gdx.input.isKeyPressed(Keys.R) && rPressed)
			rPressed = false;

		if (running) {
			floors.forEach(x -> {
				x.update(scrR, myDoodle.getFootRect(), !myDoodle.isJumping());
				if (x.effect == Effect.COMMON_JUMP_CAUSER) {
					myDoodle.doJump();
					x.effect = Effect.NOTHING;
				}
			});
			myDoodle.update(delta);
		}
	}

	public void render(SpriteBatch batch) {
		floors.forEach(x -> {
			x.draw(batch, scrR);
		});
		myDoodle.draw(batch, scrR);
		doodles.forEach(x -> x.draw(batch, scrR));
	}

	public int getPatternSliding() {
		return (int) (scrR.x % Res._pattern.getHeight());
	}

}
