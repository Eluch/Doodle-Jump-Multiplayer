package me.eluch.libgdx.DoJuMu;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

import me.eluch.libgdx.DoJuMu.gfx.AnimatedImage;
import me.eluch.libgdx.DoJuMu.gfx.LoadedFont;
import me.eluch.libgdx.DoJuMu.gfx.LoadedImage;

public class Res {

	public static final Random rand = new Random();

	// Resources:

	public static final LoadedFont _startScreenFont;
	public static final LoadedFont _buttonFont;
	public static final LoadedFont _optionsFont;
	public static final LoadedFont _serverListFont;

	public static final LoadedImage _madnessMe;
	public static final LoadedImage _blackhole;
	public static final LoadedImage _mob1;
	public static final LoadedImage _mob2;
	public static final LoadedImage _mob3;
	public static final LoadedImage _logo;
	public static final LoadedImage _pattern;
	public static final LoadedImage _clearpattern;
	public static final LoadedImage _jetpackfull;
	public static final LoadedImage _protectionbubbleitem;
	public static final LoadedImage _transpartentPixel;
	public static final LoadedImage _serverPicker;
	public static final LoadedImage _spacerPixel;
	public static final LoadedImage _deadEye;

	public static final AnimatedImage _button;
	public static final AnimatedImage _floorSprite;
	public static final AnimatedImage _jetpackhalf;
	public static final AnimatedImage _portal_blue;
	public static final AnimatedImage _portal_orange;
	public static final AnimatedImage _propellerhat;
	public static final AnimatedImage _protectionbubbleactive;
	public static final AnimatedImage _reverse;
	public static final AnimatedImage _spring;
	public static final AnimatedImage _springshoe;
	public static final AnimatedImage _swamp;
	public static final AnimatedImage _trampoline;
	public static final AnimatedImage _characters;
	public static final AnimatedImage _characters_t; //transparent
	public static final AnimatedImage _shoot;
	public static final AnimatedImage _posArrow_large;
	public static final AnimatedImage _posArrow_small;

	static {
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
		_deadEye = new LoadedImage("images/player/Dead_eye.png");

		_button = new AnimatedImage("images/etc/button.png", 2, 1, 10);
		_floorSprite = new AnimatedImage("images/objects/floorSprite.png", 6, 1, 10);
		_jetpackhalf = new AnimatedImage("images/objects/jetpackhalf.png", 1, 6, 10);
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
		_characters_t = new AnimatedImage("images/player/Characters_transparent.png", 2, 2, 10);
		_shoot = new AnimatedImage("images/player/shoot.png", 2, 2, 10);
		_posArrow_large = new AnimatedImage("images/etc/posarrow_large.png", 1, 2, 10);
		_posArrow_small = new AnimatedImage("images/etc/posarrow_small.png", 1, 2, 10);
	}

	public static void load() {
		System.out.println("Resources loaded");
	}
}
