package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.MenuHandler;
import me.eluch.libgdx.DoJuMu.MenuHandler.Handle;
import me.eluch.libgdx.DoJuMu.MenuHandler.Layout;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Options.OptionProperty;
import me.eluch.libgdx.DoJuMu.Res;

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
	private MenuHandler menu;

	private int dividedWidth;
	private int dividedHeight;

	public OptionMenuScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch) {
		// this.game = game;
		this.camera = camera;
		this.batch = batch;

		dividedWidth = (int) (camera.viewportWidth / Res._pattern.getWidth()) + 1;
		dividedHeight = (int) (camera.viewportHeight / Res._pattern.getHeight()) + 1;

		menu = new MenuHandler(Layout.Vertical, Handle.KeyboardAndMouse, (int) (camera.viewportWidth / 2) - Res._button.getWidth() / 2, (int) (camera.viewportHeight - Res._logo.getHeight() - 100));
		menu.addMenuItem("Player name", Res._button, Res._buttonFont, () -> {
			Options.changeName();
		});
		menu.addMenuItem("Sound", Res._button, Res._buttonFont, () -> {
			Options.setProperty(OptionProperty.sound);
		});
		menu.addMenuItem("UPnP", Res._button, Res._buttonFont, () -> {
			Options.setProperty(OptionProperty.upnp);
		});
		menu.addMenuItem("Character", Res._button, Res._buttonFont, () -> {
			Options.setProperty(OptionProperty.character);
		});
		menu.addMenuItem("Back", Res._button, Res._buttonFont, () -> {
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
			batch.draw(Res._pattern.getTexture(), 0, 0, Res._pattern.getWidth() * dividedWidth, Res._pattern.getHeight() * dividedHeight, 0, dividedHeight, dividedWidth, 0); // Pattern_fulltexture
			batch.draw(Res._logo.getTexture(), camera.viewportWidth / 2 - Res._logo.getWidth() / 2, camera.viewportHeight - Res._logo.getHeight() - 20);
			drawCurrentOption();
			menu.draw(batch);
		}
		batch.end();
	}

	private void drawCurrentOption() {
		if (menu.getSelectedName().equals("Player name")) {
			Res._optionsFont.drawLeft(batch, "Current name (max length: 15 char): " + Options.getName(), 25f, 25f);
		} else if (menu.getSelectedName().equals("Sound")) {
			Res._optionsFont.drawLeft(batch, "Sound: " + (Options.isSoundEnabled() ? "Enabled" : "Disabled"), 25f, 25f);
		} else if (menu.getSelectedName().equals("UPnP")) {
			Res._optionsFont.drawLeft(batch, "This needs to be enable if you are behind a home router", 25f, 25f);
			Res._optionsFont.drawLeft(batch, "UPnP: " + (Options.isUpnpEnabled() ? "Enabled" : "Disabled"), 25f, 30f + Res._optionsFont.getSize());
		} else if (menu.getSelectedName().equals("Character")) {
			batch.draw(Res._characters.getSpecificImage(Options.getCharacter().ordinal() * 2), 50f, 25f);
			Res._optionsFont.drawLeft(batch, "Current Character:", 25f, 35f + Res._characters.getHeight());
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
