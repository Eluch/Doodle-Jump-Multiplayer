package me.eluch.libgdx.DoJuMu.game;

import java.util.ArrayList;
import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.game.doodle.Doodle;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.BlackHole;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.Enemy;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.game.item.Item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameObjectContainer {

	//Just for degug
	private boolean running = false;
	private boolean rPressed = false;

	protected ArrayList<Doodle> players = new ArrayList<>();
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected ArrayList<BlackHole> blackHoles = new ArrayList<>();
	protected ArrayList<Floor> floors = new ArrayList<>();
	protected ArrayList<Item> items = new ArrayList<>();

	protected Doodle myDoodle;
	protected Rectangle scrR = new Rectangle(0, 0, Options.ScreenRes.width / 2, Options.ScreenRes.height); // Screen Rectangle

	public GameObjectContainer(CorePlayerContainer<?> players) { // CONSTRUCTOR
		myDoodle = new Doodle(players.getMySelf().getId(), new Random().nextInt(Options.ScreenRes.width / 2 - Res._characters.getWidth()), 10, Options.getCharacter());
		players.getPlayers().forEach(x -> {
			this.players.add(new Doodle(x.getId(), 10, 10, x.getGenderType()));
		});
	}

	public void update() {
		if (Gdx.input.isKeyPressed(Keys.R) && !rPressed) {
			rPressed = true;
			running = !running;
		} else if (!Gdx.input.isKeyPressed(Keys.R) && rPressed)
			rPressed = false;

		if (running) {
			myDoodle.setJumping(false);
		} else 
			myDoodle.setJumping(true);
	}

	public void render(SpriteBatch batch) {
		myDoodle.draw(batch, scrR);
	}

	public int getPatternSliding() {
		return (int) (scrR.x % Res._pattern.getHeight());
	}

}
