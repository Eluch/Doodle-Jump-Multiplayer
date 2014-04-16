package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.GameObjectContainer;
import me.eluch.libgdx.DoJuMu.game.GameRole;
import me.eluch.libgdx.DoJuMu.network.client.Client;
import me.eluch.libgdx.DoJuMu.network.server.Server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Game game;

	private int dividedWidthPP; // PlayerPlace
	private int dividedHeightPP;
	private int dividedWidthSP; // ScorePlace
	private int dividedHeightSP;

	private GameRole role;
	private Server server;
	private Client client;

	private GameObjectContainer gameObjects;

	public GameScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch, GameRole role, Object connection) {
		if (!(connection instanceof Server) && !(connection instanceof Client)) {
			System.err.println("Got wrong connection! Should be Server or Client");
			System.exit(1);
		}
		this.game = game;
		this.camera = camera;
		this.batch = batch;

		dividedWidthPP = (int) (camera.viewportWidth / 2 / Res._pattern.getWidth()) + 1;
		dividedHeightPP = (int) (camera.viewportHeight / Res._pattern.getHeight()) + 1;
		dividedWidthSP = (int) (camera.viewportHeight / Res._clearpattern.getWidth()) + 1;
		dividedHeightSP = (int) (camera.viewportHeight / Res._clearpattern.getHeight()) + 1;

		//this.connection = connection;
		this.role = role;
		if (connection instanceof Server && this.role == GameRole.SERVER) {
			server = (Server) connection;
			gameObjects = new GameObjectContainer(server.getPlayers());
		} else if (connection instanceof Client && this.role == GameRole.CLIENT) {
			client = (Client) connection;
			gameObjects = new GameObjectContainer(client.getPlayers());
		}
	}

	private void update(float delta) {
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			switch (role) {
			case CLIENT:
				client.stop();
				break;
			case SERVER:
				server.stop();
				break;
			}
			game.setScreen(new MainMenuScreen(game, camera, batch));
		}
		gameObjects.update();
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
			batch.draw(Res._pattern.getTexture(), 0, 0 - gameObjects.getPatternSliding(), Res._pattern.getWidth() * dividedWidthPP, Res._pattern.getHeight() * dividedHeightPP, 0, dividedHeightPP,
					dividedWidthPP, 0);
			batch.draw(Res._clearpattern.getTexture(), camera.viewportWidth / 2, 0, Res._clearpattern.getWidth() * dividedWidthSP, Res._clearpattern.getHeight() * dividedHeightSP, 0, dividedHeightSP,
					dividedWidthSP, 0);
			batch.draw(Res._spacerPixel.getTexture(), camera.viewportWidth / 2, 0, 10, camera.viewportHeight, 0, 1, 1, 0);

			gameObjects.render(batch);
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
