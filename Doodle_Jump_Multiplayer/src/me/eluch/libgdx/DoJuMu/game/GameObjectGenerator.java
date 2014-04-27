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
	private final int MAX_NOGENERATE = 150; //150 Default
	private int highest_jumpable = 200;
	private int generateCounter = 0; //counts as many times as generate() run.
	private int generation_rate = 50;
	private final int GENERATION_RATE_INCREMENT = 2;

	private final float blue_speed_multiper = 0.5f;
	private final float gray_speed_multiper = 0.3f;

	private int cfPrescaler = 0; //c like chance to
	private final int CF_PRESCALER_TARGET = 20;
	private int cf_green = 10000;
	private int cf_blue = 7000;
	private int cf_yellow = 1000;
	private int cf_gray = 6000;
	private int cf_white = 2000;

	private static final int ITEM_GEN_CHANCE = 15;
	private final int ci_jetpack = 1;
	private final int ci_propellerHat = 3;
	private final int ci_shield = 5;
	private final int ci_spring = 20;
	private final int ci_springShoe = 7;
	private final int ci_trampoline = 10;

	private boolean lastWasWhite = false;

	public GameObjectGenerator(GameObjectContainer gameObjects, Server server) {
		this.gameObjects = gameObjects;
		this.server = server;
	}

	private void generate() {
		generateCounter++;
		generation_rate += GENERATION_RATE_INCREMENT;
		if (generation_rate > MAX_NOGENERATE)
			generation_rate = MAX_NOGENERATE;
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
					f = new BlueFloor(x, y, generateCounter * blue_speed_multiper);
					break;
				case GRAY:
					generateItem = true;
					lastWasWhite = false;
					f = new GrayFloor(x, y, generateCounter * gray_speed_multiper);
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

					Item item = null;
					if (generateItem) {
						int genChance = r.nextInt() % ITEM_GEN_CHANCE; // 1 to ITEM_GEN_CHANCE to generate item
						if (genChance == 1) {
							rInt = r.nextInt(getItemChances());
							ItemType itemType = getTypeFromItemChance(rInt);

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
							}
						}
					} // End of item generation
					if (item == null)
						server.sendToAllPlayersWithTCP(f.encode());
					else
						server.sendToAllPlayersWithTCP(f.encode().writeBytes(item.encode()));
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
		if (++cfPrescaler >= CF_PRESCALER_TARGET) {
			cfPrescaler = 0;
			cf_green -= 5;
			if (cf_green < 1000)
				cf_green = 1000;
			cf_blue += 7;
			cf_yellow += 10;
			if (cf_yellow > cf_blue / 2)
				cf_yellow = cf_blue / 2;
			cf_gray += 5;
			cf_white += 6;
		}
		return (cf_green + cf_blue + cf_yellow + cf_gray + cf_white);
	}

	private FloorType getTypeFromFloorChance(int num) {
		if (cf_green >= num)
			return FloorType.GREEN;
		num -= cf_green;
		if (cf_blue >= num)
			return FloorType.BLUE;
		num -= cf_blue;
		if (cf_yellow >= num)
			return FloorType.YELLOW;
		num -= cf_yellow;
		if (cf_gray >= num)
			return FloorType.GRAY;
		num -= cf_gray;
		if (cf_white >= num)
			return FloorType.WHITE;
		return FloorType.GREEN; //If something went wrong
		// green, blue, yellow, gray, white <-- Chances in order
	}

	private int getItemChances() {
		return (ci_jetpack + ci_propellerHat + ci_shield + ci_spring + ci_springShoe + ci_trampoline);
	}

	private ItemType getTypeFromItemChance(int num) {
		if (ci_jetpack >= num)
			return ItemType.JETPACK;
		num -= ci_jetpack;
		if (ci_propellerHat >= num)
			return ItemType.PROPELLER_HAT;
		num -= ci_propellerHat;
		if (ci_shield >= num)
			return ItemType.SHIELD;
		num -= ci_shield;
		if (ci_spring >= num)
			return ItemType.SPRING;
		num -= ci_spring;
		if (ci_springShoe >= num)
			return ItemType.SPRING_SHOE;
		num -= ci_springShoe;
		if (ci_trampoline >= num)
			return ItemType.TRAMPOLINE;
		return ItemType.SPRING;
	}
}
