package me.eluch.libgdx.DoJuMu;

import me.eluch.libgdx.DoJuMu.screens.StartScreen;
import me.eluch.libgdx.DoJuMu.screens.StartScreen.StartType;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Doodle_Jump_Multiplayer extends Game {

	OrthographicCamera camera;
	SpriteBatch batch;

	private void init() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Options.ScreenRes.initRes((int) camera.viewportWidth, (int) camera.viewportHeight);
		batch = new SpriteBatch();

		Res.load();
	}

	@Override
	public void create() {
		init();
		
		//First appearable screen
		setScreen(new StartScreen(this, camera, batch, StartType.programstart));
	}

}
