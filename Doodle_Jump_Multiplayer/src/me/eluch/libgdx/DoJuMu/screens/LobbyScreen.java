package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.MenuHandler;
import me.eluch.libgdx.DoJuMu.MenuHandler.Handle;
import me.eluch.libgdx.DoJuMu.MenuHandler.Layout;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.Resources;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.network.client.Client;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LobbyScreen implements Screen {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Resources res;
	private MenuHandler menu;
	private Game game;

	private int dividedWidth;
	private int dividedHeight;

	private final Client client;

	public LobbyScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch, final Client client) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;
		res = Resources.i;
		this.client = client;

		dividedWidth = (int) (camera.viewportWidth / res._pattern.getWidth()) + 1;
		dividedHeight = (int) (camera.viewportHeight / res._pattern.getHeight()) + 1;

		menu = new MenuHandler(Layout.Horizonal, Handle.KeyboardAndMouse, 10, (int) (camera.viewportHeight - res._logo.getHeight() - 100));
		menu.addMenuItem("Exit Lobby", res._button, res._buttonFont, () -> {
			client.stop();
			game.setScreen(new JoinScreen(game, camera, batch));
		});

		// request for all players
		WriteOnlyPacket oPacket = new WriteOnlyPacket(PacketType.REQUEST_ALL_PLAYERS);
		client.getTcpChannel().writeAndFlush(oPacket.getByteBuf());
	}

	private void update(float delta) {
		menu.handle(camera, Options.ScreenRes.width, Options.ScreenRes.height);
		if (!client.isRunning()) {
			client.stop();
			game.setScreen(new JoinScreen(game, camera, batch));
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
			batch.draw(res._pattern.getTexture(), 0, 0, res._pattern.getWidth() * dividedWidth, res._pattern.getHeight() * dividedHeight, 0, dividedHeight, dividedWidth, 0);
			batch.draw(res._logo.getTexture(), camera.viewportWidth / 2 - res._logo.getWidth() / 2, camera.viewportHeight - res._logo.getHeight() - 20);
			menu.draw(batch);
			res._buttonFont.drawLeft(batch, "Player Name", 100, 400);
			res._buttonFont.drawLeft(batch, "Ping", 500, 400);
			res._buttonFont.drawLeft(batch, "ID", 700, 400);
			batch.draw(res._transpartentPixel.getTexture(), 100, 390, camera.viewportWidth - 200, -350);
			int currY = 350;
			for (CorePlayer player : client.getPlayers().getPlayers()) {
				res._serverListFont.drawLeft(batch, player.getName(), 120, currY);
				res._serverListFont.drawLeft(batch, "" + player.getPing(), 520, currY);
				res._serverListFont.drawLeft(batch, "" + player.getId(), 720, currY);
				currY -= 25;
			}
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
