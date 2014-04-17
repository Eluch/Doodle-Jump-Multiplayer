package me.eluch.libgdx.DoJuMu.game;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.Options.ScreenRes;
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

	public GameObjectGenerator(GameObjectContainer gameObjects, Server server) {
		this.gameObjects = gameObjects;
		this.server = server;

		generate(200, 50000);
	}

	public void generate(int from, int to) {
		for (int i = from; i < to; i += 50) {
			int x = r.nextInt(ScreenRes.width / 2);
			int floorType = r.nextInt(6);
			switch (floorType) {
			case 0:
				gameObjects.floors.add(new BlueFloor(x, i));
				break;
			case 1:
				gameObjects.floors.add(new BrownFloor(x, i));
				break;
			case 2:
				gameObjects.floors.add(new GrayFloor(x, i, 10));
				break;
			case 3:
				gameObjects.floors.add(new GreenFloor(x, i));
				break;
			case 4:
				gameObjects.floors.add(new WhiteFloor(x, i));
				break;
			case 5:
				gameObjects.floors.add(new YellowFloor(x, i));
				break;

			default:
				break;
			}
		}
	}

}
