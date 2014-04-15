package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.MenuHandler;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.MenuHandler.Handle;
import me.eluch.libgdx.DoJuMu.MenuHandler.Layout;
import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.screens.StartScreen.StartType;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {

	//private Game game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Resources res;
	private MenuHandler menu;

	private int dividedWidth;
	private int dividedHeight;

	public MainMenuScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch) {
		//this.game = game;
		this.camera = camera;
		this.batch = batch;
		res = Resources.i;

		dividedWidth = (int) (camera.viewportWidth / res._pattern.getWidth()) + 1;
		dividedHeight = (int) (camera.viewportHeight / res._pattern.getHeight()) + 1;

		menu = new MenuHandler(Layout.Vertical, Handle.KeyboardAndMouse, (int) (camera.viewportWidth / 2) - res._button.getWidth() / 2, (int) (camera.viewportHeight - res._logo.getHeight() - 100));
		menu.addMenuItem("Join Game", res._button, res._buttonFont, new Runnable() {
			
			@Override
			public void run() {
				game.setScreen(new JoinScreen(game, camera, batch));
			}
		});
		menu.addMenuItem("Host Game", res._button, res._buttonFont, new Runnable() {
			
			@Override
			public void run() {
				game.setScreen(new HostScreen(game, camera, batch));
			}
		});
		menu.addMenuItem("Options", res._button, res._buttonFont, new Runnable() {

			@Override
			public void run() {
				game.setScreen(new OptionMenuScreen(game, camera, batch));
			}
		});
		menu.addMenuItem("Credits", res._button, res._buttonFont, new Runnable() {
			
			@Override
			public void run() {
				game.setScreen(new StartScreen(game, camera, batch, StartType.credits));
			}
		}); // TODO to_loading_screen
		menu.addMenuItem("Exit", res._button, res._buttonFont, new Runnable() {

			@Override
			public void run() {
				Gdx.app.exit();
			}
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
			batch.draw(res._pattern.getTexture(), 0, 0, res._pattern.getWidth() * dividedWidth, res._pattern.getHeight() * dividedHeight, 0, dividedHeight, dividedWidth, 0);
			batch.draw(res._logo.getTexture(), camera.viewportWidth / 2 - res._logo.getWidth() / 2, camera.viewportHeight - res._logo.getHeight() - 20);
			menu.draw(batch);
		}
		batch.end();
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
