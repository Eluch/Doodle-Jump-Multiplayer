package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen implements Screen {

	private Game game;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private float elapsed;
	private final StartType st;
	private boolean escPressed;

	public StartScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch, StartType st) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;
		elapsed = 0;
		this.st = st;
		escPressed = false;
	}

	private void update(float delta) {
		switch (st) {
		case programstart:
			elapsed += delta;
			if (elapsed >= 2f) {
				game.setScreen(new MainMenuScreen(game, camera, batch));
			}
			break;
		case credits:
			if (Gdx.input.isKeyPressed(Keys.ESCAPE) && !escPressed) {
				escPressed = true;
			} else if (!Gdx.input.isKeyPressed(Keys.ESCAPE) && escPressed) {
				game.setScreen(new MainMenuScreen(game, camera, batch));
			}
			break;
		}
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
			batch.draw(Res._madnessMe.getImage(), 10, 10);
			Res._startScreenFont.drawRight(batch, "by Eluch", camera.viewportWidth - 10, 10);

			Res._startScreenFont.drawCenter(batch, "E-mail:", camera.viewportWidth / 2, 10 + Res._startScreenFont.getSize() + Res._startScreenFont.getSize() / 2);
			Res._startScreenFont.drawCenter(batch, "pkferi@gmail.com", camera.viewportWidth / 2, 10 + Res._startScreenFont.getSize() / 2);

			Res._startScreenFont.drawCenter(batch, "Doodle Jump Multiplayer (v" + Options.VERSION + ")", camera.viewportWidth / 2, camera.viewportHeight - 50);
			if (st.equals(StartType.programstart))
				Res._startScreenFont.drawCenter(batch, "Game is starting...", camera.viewportWidth / 2, camera.viewportHeight - 100);
			else if (st.equals(StartType.credits))
				Res._startScreenFont.drawCenter(batch, "Press esc to go back to mainmenu", camera.viewportWidth / 2, camera.viewportHeight - 100);
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

	public enum StartType {
		programstart, credits;
	}

}
