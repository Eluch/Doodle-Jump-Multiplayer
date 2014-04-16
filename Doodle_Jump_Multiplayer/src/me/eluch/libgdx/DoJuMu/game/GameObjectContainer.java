package me.eluch.libgdx.DoJuMu.game;

import java.util.ArrayList;

import me.eluch.libgdx.DoJuMu.game.doodle.Doodle;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.BlackHole;
import me.eluch.libgdx.DoJuMu.game.enemy_obj.Enemy;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.game.item.Item;

public class GameObjectContainer {
	
	protected ArrayList<Doodle> players = new ArrayList<>();
	protected ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	protected ArrayList<BlackHole> blackHoles = new ArrayList<>();
	protected ArrayList<Floor> floors = new ArrayList<>();
	protected ArrayList<Item> items = new ArrayList<>();

}
