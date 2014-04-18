package me.eluch.libgdx.DoJuMu.game;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.Options.ScreenRes;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.floors.BlueFloor;
import me.eluch.libgdx.DoJuMu.game.floors.BrownFloor;
import me.eluch.libgdx.DoJuMu.game.floors.GrayFloor;
import me.eluch.libgdx.DoJuMu.game.floors.GreenFloor;
import me.eluch.libgdx.DoJuMu.game.floors.WhiteFloor;
import me.eluch.libgdx.DoJuMu.game.floors.YellowFloor;
import me.eluch.libgdx.DoJuMu.network.server.Server;

public final class GameObjectGenerator {

	private final GameObjectContainer gameObjects;
	private final Server server;

	private final Random r = new Random();

	private final int GENERATE_SAMETIME = 10000;
	private int highest_jumpable = 200;
	private int generateCounter = 0; //counts as many times as generate() run.
	private int generation_rate = 50;

	public GameObjectGenerator(GameObjectContainer gameObjects, Server server) {
		this.gameObjects = gameObjects;
		this.server = server;

		generate();
	}

	private void generate() {
		generateCounter++;
		generation_rate += 5;
		if (generation_rate > 150)
			generation_rate = 150;
		int from = highest_jumpable;
		int to = from + GENERATE_SAMETIME;
		System.out.println("Generating platforms: " + from + " - " + to); // #### DEBUG PRINT ####
		for (int y = from; y < to; y += generation_rate) {
			int x = r.nextInt(Options.GAME_PLACE_WIDTH - Res._floorSprite.getWidth());
			int floorType = r.nextInt(6);
			switch (floorType) {
			case 0:
				gameObjects.floors.add(new BlueFloor(x, y, generateCounter * 0.5f));
				break;
			case 1:
				gameObjects.floors.add(new BrownFloor(x, y));
				//break;
			case 2:
				gameObjects.floors.add(new GrayFloor(x, y, generateCounter * 0.3f));
				break;
			case 3:
				gameObjects.floors.add(new GreenFloor(x, y));
				break;
			case 4:
				gameObjects.floors.add(new WhiteFloor(x, y));
				break;
			case 5:
				gameObjects.floors.add(new YellowFloor(x, y, 500 + generation_rate));
				break;

			default:
				break;
			}
			highest_jumpable = y;
		}
	}

	public void checkForNeedGeneration() {
		int highest = (int) gameObjects.myDoodle.rec.y;
		for (DoodleBasic d : gameObjects.doodles) {
			if (d.rec.y > highest) {
				highest = (int) d.rec.y;
			}
		}

		if (highest + GENERATE_SAMETIME > highest_jumpable)
			generate();
	}
}
