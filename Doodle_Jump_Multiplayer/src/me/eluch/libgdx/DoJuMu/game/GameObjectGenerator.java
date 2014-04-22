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
import me.eluch.libgdx.DoJuMu.game.item.Item;
import me.eluch.libgdx.DoJuMu.game.item.ItemType;
import me.eluch.libgdx.DoJuMu.game.item.Jetpack;
import me.eluch.libgdx.DoJuMu.game.item.PropellerHat;
import me.eluch.libgdx.DoJuMu.game.item.Shield;
import me.eluch.libgdx.DoJuMu.game.item.Spring;
import me.eluch.libgdx.DoJuMu.game.item.SpringShoe;
import me.eluch.libgdx.DoJuMu.game.item.Trampoline;
import me.eluch.libgdx.DoJuMu.network.server.Server;

public final class GameObjectGenerator {

	private final GameObjectContainer gameObjects;
	private final Server server;

	private final Random r = new Random();

	private final int GENERATE_SAMETIME = 10000;
	private final int MAX_NOGENERATE = 125; //150 Default
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

	private static final int ITEM_GEN_CHANCE = 15;
	private final int c_jetpack = 1;
	private final int c_propellerHat = 3;
	private final int c_shield = 5;
	private final int c_spring = 20;
	private final int c_springShoe = 7;
	private final int c_trampoline = 10;

	private boolean lastWasWhite = false;

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
				int rInt;
				boolean generateItem = false;
				FloorType floorType;
				Floor f = null;
				do {
					rInt = r.nextInt(changeAndGetFloorChances());
					floorType = getTypeFromFloorChance(rInt);
				} while (lastWasWhite && floorType == FloorType.GRAY);

				switch (floorType) {
				case BLUE:
					generateItem = true;
					lastWasWhite = false;
					f = new BlueFloor(x, y, generateCounter * 0.4f);
					break;
				case GRAY:
					generateItem = true;
					lastWasWhite = false;
					f = new GrayFloor(x, y, generateCounter * 0.2f);
					break;
				case GREEN:
					generateItem = true;
					lastWasWhite = false;
					f = new GreenFloor(x, y);
					break;
				case WHITE:
					f = new WhiteFloor(x, y);
					lastWasWhite = true;
					break;
				case YELLOW:
					f = new YellowFloor(x, y, 500 + generation_rate);
					lastWasWhite = false;
					break;
				default:
					System.err.println("Generator error: Floor default statement reached.");
					break;
				}

				if (f != null) {
					gameObjects.getFloors().add(f);
					highest_jumpable = y;
					server.sendToAllPlayersWithTCP(f.encode());

					if (generateItem) {
						int genChance = r.nextInt() % ITEM_GEN_CHANCE; // 1 to ITEM_GEN_CHANCE to generate item
						if (genChance == 1) {
							rInt = r.nextInt(getItemChances());
							ItemType itemType = getTypeFromItemChance(rInt);
							Item item = null;
							switch (itemType) {
							case JETPACK:
								item = new Jetpack(f);
								break;
							case PROPELLER_HAT:
								item = new PropellerHat(f);
								break;
							case SHIELD:
								item = new Shield(f);
								break;
							case SPRING:
								item = new Spring(f);
								break;
							case SPRING_SHOE:
								item = new SpringShoe(f);
								break;
							case TRAMPOLINE:
								item = new Trampoline(f);
								break;
							default:
								System.err.println("Generator error: Item default statement reached.");
								break;
							}

							if (item != null) {
								gameObjects.getItems().add(item);
								server.sendToAllPlayersWithTCP(item.encode());
							}
						}
					}
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

	private int changeAndGetFloorChances() {
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

	private FloorType getTypeFromFloorChance(int num) {

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

	private int getItemChances() {
		return (c_jetpack + c_propellerHat + c_shield + c_spring + c_springShoe + c_trampoline);
	}

	private ItemType getTypeFromItemChance(int num) {
		if (c_jetpack >= num)
			return ItemType.JETPACK;
		num -= c_jetpack;
		if (c_propellerHat >= num)
			return ItemType.PROPELLER_HAT;
		num -= c_propellerHat;
		if (c_shield >= num)
			return ItemType.SHIELD;
		num -= c_shield;
		if (c_spring >= num)
			return ItemType.SPRING;
		num -= c_spring;
		if (c_springShoe >= num)
			return ItemType.SPRING_SHOE;
		num -= c_springShoe;
		if (c_trampoline >= num)
			return ItemType.TRAMPOLINE;
		return ItemType.SPRING;
	}
}
