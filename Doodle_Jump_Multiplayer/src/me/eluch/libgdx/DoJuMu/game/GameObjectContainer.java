package me.eluch.libgdx.DoJuMu.game;

import java.util.ArrayList;
import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.game.doodle.Doodle;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.BlackHole;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.Enemy;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.game.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObjectContainer {

	protected ArrayList<Doodle> players = new ArrayList<>();
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected ArrayList<BlackHole> blackHoles = new ArrayList<>();
	protected ArrayList<Floor> floors = new ArrayList<>();
	protected ArrayList<Item> items = new ArrayList<>();

	protected Doodle myDoodle;

	public GameObjectContainer(CorePlayerContainer<CorePlayer> players) { // CONSTRUCTOR
		myDoodle = new Doodle(players.getMySelf().getId(), new Random().nextInt(Options.ScreenRes.width / 2 - Res._characters.getWidth()), 10, Options.getCharacter());
		players.getPlayers().forEach(x -> {
			this.players.add(new Doodle(x.getId(), 10, 10, x.getGenderType()));
		});
	}

	public void update() {

	}

	public void render(SpriteBatch batch) {

	}

}
