package me.eluch.libgdx.DoJuMu;

import java.util.Random;

import me.eluch.libgdx.DoJuMu.game.doodle.DoodleGenderType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Preferences;

public class Options {

	public static final int SERVER_PORT = 16160;
	public static final int CLIENT_PORT = 16161;
	public static final String VERSION = "0.8"; //Not released
	public static final int MAXPLAYERS = 13; //13 The correct max
	public static final long DISCOVER_TIMEOUT = 1000;
	public static final int PING_FREQUENCY = 3000;
	public static final int GAME_PLACE_WIDTH = 480;
	public static final int GAME_PLACE_HEIGHT = 720;

	private static String name;
	private static boolean sound;
	private static boolean upnp;
	private static DoodleGenderType character;

	private static int randomKey;

	private static boolean changeInProcess;

	private static final String path = "dojumu.conf";
	private static final Preferences prefs = Gdx.app.getPreferences(path);

	static {
		changeInProcess = false;
		Random r = new Random();
		if (!prefs.contains("name")) {
			prefs.putString("name", "Rand" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10));
		}
		if (!prefs.contains("sound")) {
			prefs.putBoolean("sound", true);
		}
		if (!prefs.contains("upnp")) {
			prefs.putBoolean("upnp", true);
		}
		if (!prefs.contains("character")) {
			prefs.putInteger("character", 0);
		}
		if (!prefs.contains("randomKey")) {
			prefs.putInteger("randomKey", new Random(System.currentTimeMillis()).nextInt());
		}
		name = prefs.getString("name");
		sound = prefs.getBoolean("sound");
		upnp = prefs.getBoolean("upnp");
		character = DoodleGenderType.values()[prefs.getInteger("character")];
		randomKey = prefs.getInteger("randomKey");
		prefs.flush();
	}

	public static void setProperty(OptionProperty op) {
		switch (op) {
		case character:
			character = DoodleGenderType.values()[(character.ordinal() + 1) % DoodleGenderType.values().length];
			prefs.putInteger("character", character.ordinal());
			break;
		case sound:
			sound = !sound;
			prefs.putBoolean("sound", sound);
			break;
		case upnp:
			upnp = !upnp;
			prefs.putBoolean("upnp", upnp);
			break;
		}
		prefs.flush();
	}

	public static String getName() {
		return name;
	}

	public static boolean isSoundEnabled() {
		return sound;
	}

	public static boolean isUpnpEnabled() {
		return upnp;
	}

	public static DoodleGenderType getCharacter() {
		return character;
	}

	public static void changeName() {
		if (Options.changeInProcess)
			return;
		Options.changeInProcess = true;
		TextInputListener inputListener = new TextInputListener() {

			@Override
			public void input(String text) {
				if (text.length() > 0) {
					if (text.length() > 15) {
						text = text.substring(0, 15);
					}
					name = text;
					prefs.putString("name", name);
					prefs.flush();
				}
				Options.changeInProcess = false;
			}

			@Override
			public void canceled() {
				Options.changeInProcess = false;
			}
		};

		Gdx.input.getTextInput(inputListener, "Enter your new name!", name);
	}

	public static String getHash() {
		return name + randomKey;
	}

	// Additional classes, enums

	public static class ScreenRes {
		public static int width, height;

		static {
			width = height = -1;
		}

		public static void setRes(int newWidth, int newHeight) {
			width = newWidth;
			height = newHeight;
		}

		public static void initRes(int newWidth, int newHeight) {
			if (width == -1 && height == -1) {
				width = newWidth;
				height = newHeight;
			}
		}
	}

	public enum OptionProperty {
		sound, upnp, character;
	}
}
