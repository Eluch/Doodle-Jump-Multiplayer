package me.eluch.libgdx.DoJuMu;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

// Desktop Main starter

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Doodle Jump Multiplayer";
		cfg.useGL20 = true;
		cfg.width = 960;
		cfg.height = 720;
		cfg.vSyncEnabled = true;
		cfg.resizable = false;
		cfg.backgroundFPS = cfg.foregroundFPS = 60;
		new LwjglApplication(new Doodle_Jump_Multiplayer(), cfg);
	}
}
