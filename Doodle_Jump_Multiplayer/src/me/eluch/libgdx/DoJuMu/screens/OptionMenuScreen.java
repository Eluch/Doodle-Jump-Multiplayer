package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.MenuHandler;
import me.eluch.libgdx.DoJuMu.MenuHandler.Handle;
import me.eluch.libgdx.DoJuMu.MenuHandler.Layout;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Options.OptionProperty;
import me.eluch.libgdx.DoJuMu.Resources;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionMenuScreen implements Screen {

	// private Game game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Resources res;
	private MenuHandler menu;

	private int dividedWidth;
	private int dividedHeight;

	public OptionMenuScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch) {
		// this.game = game;
		this.camera = camera;
		this.batch = batch;
		res = Resources.i;

		dividedWidth = (int) (camera.viewportWidth / res._pattern.getWidth()) + 1;
		dividedHeight = (int) (camera.viewportHeight / res._pattern.getHeight()) + 1;

		menu = new MenuHandler(Layout.Vertical, Handle.KeyboardAndMouse, (int) (camera.viewportWidth / 2) - res._button.getWidth() / 2, (int) (camera.viewportHeight - res._logo.getHeight() - 100));
		menu.addMenuItem("Player name", res._button, res._buttonFont, () -> {
			Options.changeName();
		});
		menu.addMenuItem("Sound", res._button, res._buttonFont, () -> {
			Options.setProperty(OptionProperty.sound);
		});
		menu.addMenuItem("UPnP", res._button, res._buttonFont, () -> {
			Options.setProperty(OptionProperty.upnp);
		});
		menu.addMenuItem("Character", res._button, res._buttonFont, () -> {
			Options.setProperty(OptionProperty.character);
		});
		menu.addMenuItem("Back", res._button, res._buttonFont, () -> {
			game.setScreen(new MainMenuScreen(game, camera, batch));
		});
	}

	private void update(float delta) {
		menu.handle(camera, Options.ScreenRes.width, Options.ScreenRes.height);
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		{
			batch.draw(res._pattern.getTexture(), 0, 0, res._pattern.getWidth() * dividedWidth, res._pattern.getHeight() * dividedHeight, 0, dividedHeight, dividedWidth, 0); // Pattern_fulltexture
			batch.draw(res._logo.getTexture(), camera.viewportWidth / 2 - res._logo.getWidth() / 2, camera.viewportHeight - res._logo.getHeight() - 20);
			drawCurrentOption();
			menu.draw(batch);
		}
		batch.end();
	}

	private void drawCurrentOption() {
		if (menu.getSelectedName().equals("Player name")) {
			res._optionsFont.drawLeft(batch, "Current name (max length: 15 char): " + Options.getName(), 25f, 25f);
		} else if (menu.getSelectedName().equals("Sound")) {
			res._optionsFont.drawLeft(batch, "Sound: " + (Options.isSoundEnabled() ? "Enabled" : "Disabled"), 25f, 25f);
		} else if (menu.getSelectedName().equals("UPnP")) {
			res._optionsFont.drawLeft(batch, "This needs to be enable if you are behind a home router", 25f, 25f);
			res._optionsFont.drawLeft(batch, "UPnP: " + (Options.isUpnpEnabled() ? "Enabled" : "Disabled"), 25f, 30f + res._optionsFont.getSize());
		} else if (menu.getSelectedName().equals("Character")) {
			batch.draw(res._characters.getSpecificImage(Options.getCharacter().ordinal() * 2), 50f, 25f);
			res._optionsFont.drawLeft(batch, "Current Character:", 25f, 35f + res._characters.getHeight());
		}
	}

	@Override
	public void resize(int width, int height) {
		Options.ScreenRes.setRes(width, height);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
