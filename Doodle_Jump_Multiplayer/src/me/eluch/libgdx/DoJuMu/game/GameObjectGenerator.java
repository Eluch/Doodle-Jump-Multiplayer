package me.eluch.libgdx.DoJuMu.game;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.game.floors.BlueFloor;
import me.eluch.libgdx.DoJuMu.game.floors.BrownFloor;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.game.floors.FloorType;
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
	private final int MAX_NOGENERATE = 150;
	private int highest_jumpable = 200;
	private int generateCounter = 0; //counts as many times as generate() run.
	private int generation_rate = 50;

	private int cPrescaler = 0; //c like chance to
	private final int C_PRESCALER_TARGET = 20;
	private int c_green = 10000;
	private int c_blue = 7000;
	private int c_yellow = 1000;
	private int c_gray = 6000;
	private int c_white = 2000;

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
		boolean need2Gen;

		System.out.println("Generating platforms: " + from + " - " + to); // #### DEBUG PRINT ####

		for (int y = from; y < to; y += generation_rate) {

			int x = r.nextInt(Options.GAME_PLACE_WIDTH - Res._floorSprite.getWidth());

			need2Gen = highest_jumpable + MAX_NOGENERATE < y;

			if (!need2Gen)
				if (r.nextInt(to) >= from)
					need2Gen = true;

			if (!need2Gen)
				if (r.nextInt() % 3 == 0) {
					BrownFloor f = new BrownFloor(x, y);
					gameObjects.getFloors().add(f);
					server.sendToAllPlayersWithTCP(f.encode());
				}

			if (need2Gen) {
				int rInt = r.nextInt(changeAndGetChances());

				FloorType type = getTypeFromChance(rInt);
				Floor f = null;

				switch (type) {
				case BLUE:
					f = new BlueFloor(x, y, generateCounter * 0.5f);
					break;
				case GRAY:
					f = new GrayFloor(x, y, generateCounter * 0.3f);
					break;
				case GREEN:
					f = new GreenFloor(x, y);
					break;
				case WHITE:
					f = new WhiteFloor(x, y);
					break;
				case YELLOW:
					f = new YellowFloor(x, y, 500 + generation_rate);
					break;
				default:
					break;

				}

				//TODO generate items
				if (f != null) {
					gameObjects.getFloors().add(f);
					highest_jumpable = y;
					server.sendToAllPlayersWithTCP(f.encode());
				}
			}
		}
	}

	public void checkForNeedGeneration() {
		int highest = (int) gameObjects.getMyDoodle().rec.y;
		for (DoodleBasic d : gameObjects.getDoodles()) {
			if (d.rec.y > highest) {
				highest = (int) d.rec.y;
			}
		}

		if (highest + GENERATE_SAMETIME > highest_jumpable)
			generate();
	}

	private int changeAndGetChances() {
		if (++cPrescaler >= C_PRESCALER_TARGET) {
			cPrescaler = 0;
			c_green -= 5;
			if (c_green < 1000)
				c_green = 1000;
			c_blue += 7;
			c_yellow += 10;
			if (c_yellow > c_blue / 2)
				c_yellow = c_blue / 2;
			c_gray += 5;
			c_white += 6;
		}
		return (c_green + c_blue + c_yellow + c_gray + c_white);
	}

	private FloorType getTypeFromChance(int num) {

		if (c_green >= num)
			return FloorType.GREEN;
		num -= c_green;
		if (c_blue >= num)
			return FloorType.BLUE;
		num -= c_blue;
		if (c_yellow >= num)
			return FloorType.YELLOW;
		num -= c_yellow;
		if (c_gray >= num)
			return FloorType.GRAY;
		num -= c_gray;
		if (c_white >= num)
			return FloorType.WHITE;

		return FloorType.GREEN; //If something went wrong

		// green, blue, yellow, gray, white <-- Chances in order
	}
}
