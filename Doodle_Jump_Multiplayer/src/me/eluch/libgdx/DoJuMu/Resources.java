package me.eluch.libgdx.DoJuMu;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

import me.eluch.libgdx.DoJuMu.gfx.AnimatedImage;
import me.eluch.libgdx.DoJuMu.gfx.LoadedFont;
import me.eluch.libgdx.DoJuMu.gfx.LoadedImage;

public class Resources {

	// Making it singleton
	public static final Resources i; // As instance
	public static final Random rand;

	static {
		i = new Resources();
		rand = new Random();
	}

	// Resources:

	public final LoadedFont _startScreenFont;
	public final LoadedFont _buttonFont;
	public final LoadedFont _optionsFont;
	public final LoadedFont _serverListFont;

	public final LoadedImage _madnessMe;
	public final LoadedImage _blackhole;
	public final LoadedImage _mob1;
	public final LoadedImage _mob2;
	public final LoadedImage _mob3;
	public final LoadedImage _logo;
	public final LoadedImage _pattern;
	public final LoadedImage _clearpattern;
	public final LoadedImage _jetpackfull;
	public final LoadedImage _protectionbubbleitem;
	public final LoadedImage _transpartentPixel;
	public final LoadedImage _serverPicker;
	public final LoadedImage _spacerPixel;

	public final AnimatedImage _button;
	public final AnimatedImage _floorSprite;
	public final AnimatedImage _jetpackhalf;
	public final AnimatedImage _magicfloor;
	public final AnimatedImage _portal_blue;
	public final AnimatedImage _portal_orange;
	public final AnimatedImage _propellerhat;
	public final AnimatedImage _protectionbubbleactive;
	public final AnimatedImage _reverse;
	public final AnimatedImage _spring;
	public final AnimatedImage _springshoe;
	public final AnimatedImage _swamp;
	public final AnimatedImage _trampoline;
	public final AnimatedImage _characters;
	public final AnimatedImage _shoot;
	public final AnimatedImage _posArrow_large;
	public final AnimatedImage _posArrow_small;

	private Resources() {
		_startScreenFont = new LoadedFont("fonts/ARIALBD.TTF", 24, Color.RED);
		_buttonFont = new LoadedFont("fonts/ARIAL.TTF", 24, Color.BLACK);
		_optionsFont = new LoadedFont("fonts/ARIAL.TTF", 28, Color.BLUE);
		_serverListFont = new LoadedFont("fonts/ARIAL.TTF", 18, Color.WHITE);

		_madnessMe = new LoadedImage("images/etc/MADNESS.PNG");
		_blackhole = new LoadedImage("images/enemy/blackhole.png");
		_mob1 = new LoadedImage("images/enemy/mob1.png");
		_mob2 = new LoadedImage("images/enemy/mob2.png");
		_mob3 = new LoadedImage("images/enemy/mob3.png");
		_logo = new LoadedImage("images/etc/logo.png");
		_clearpattern = new LoadedImage("images/etc/clearpattern.png", true);
		_pattern = new LoadedImage("images/etc/pattern.png", true);
		_jetpackfull = new LoadedImage("images/objects/jetpackfull.png");
		_protectionbubbleitem = new LoadedImage("images/objects/protectionbubbleitem.png");
		_transpartentPixel = new LoadedImage("images/etc/transpix.png", true);
		_serverPicker = new LoadedImage("images/etc/ServerPicker.png");
		_spacerPixel = new LoadedImage("images/etc/spacerpixel.png", true);

		_button = new AnimatedImage("images/etc/button.png", 2, 1, 10);
		_floorSprite = new AnimatedImage("images/objects/floorSprite.png", 6, 1, 10);
		_jetpackhalf = new AnimatedImage("images/objects/jetpackhalf.png", 1, 6, 10);
		_magicfloor = new AnimatedImage("images/objects/magicfloor.png", 3, 1, 10);
		_portal_blue = new AnimatedImage("images/objects/portal_blue.png", 3, 1, 10);
		_portal_orange = new AnimatedImage("images/objects/portal_orange.png", 3, 1, 10);
		_propellerhat = new AnimatedImage("images/objects/propellerhat.png", 2, 2, 10);
		_protectionbubbleactive = new AnimatedImage("images/objects/protectionbubbleactive.png", 1, 2, 10);
		_reverse = new AnimatedImage("images/objects/reverse.png", 2, 2, 10);
		_spring = new AnimatedImage("images/objects/spring.png", 1, 2, 10);
		_springshoe = new AnimatedImage("images/objects/springshoe.png", 2, 1, 10);
		_swamp = new AnimatedImage("images/objects/swamp.png", 5, 4, 1);
		_trampoline = new AnimatedImage("images/objects/trampoline.png", 2, 1, 10);
		_characters = new AnimatedImage("images/player/Characters.png", 2, 2, 10);
		_shoot = new AnimatedImage("images/player/shoot.png", 2, 2, 10);
		_posArrow_large = new AnimatedImage("images/etc/posarrow_large.png", 1, 2, 10);
		_posArrow_small = new AnimatedImage("images/etc/posarrow_small.png", 1, 2, 10);
	}

	public void load() {
		System.out.println("Resources loaded");
	}
}
