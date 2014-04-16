package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.GameRole;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	//private Game game;

	private int dividedWidthPP; // PlayerPlace
	private int dividedHeightPP;
	private int dividedWidthSP; // ScorePlace
	private int dividedHeightSP;

	private GameRole role;

	private int patternSliding = 0;

	public GameScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch, GameRole role) {
		this.role = role;
		//this.game = game;
		this.camera = camera;
		this.batch = batch;

		dividedWidthPP = (int) (camera.viewportWidth / 2 / Res._pattern.getWidth()) + 1;
		dividedHeightPP = (int) (camera.viewportHeight / Res._pattern.getHeight()) + 1;
		dividedWidthSP = (int) (camera.viewportHeight / Res._clearpattern.getWidth()) + 1;
		dividedHeightSP = (int) (camera.viewportHeight / Res._clearpattern.getHeight()) + 1;
	}

	private void update(float delta) {
		patternSliding = (patternSliding + 1) % (int) Res._pattern.getHeight();
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
			batch.draw(Res._pattern.getTexture(), 0, 0 - patternSliding, Res._pattern.getWidth() * dividedWidthPP, Res._pattern.getHeight() * dividedHeightPP, 0, dividedHeightPP, dividedWidthPP, 0);
			batch.draw(Res._clearpattern.getTexture(), camera.viewportWidth / 2, 0, Res._clearpattern.getWidth() * dividedWidthSP, Res._clearpattern.getHeight() * dividedHeightSP, 0, dividedHeightSP,
					dividedWidthSP, 0);
			batch.draw(Res._spacerPixel.getTexture(), camera.viewportWidth / 2, 0, 10, camera.viewportHeight, 0, 1, 1, 0);
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
