package me.eluch.libgdx.DoJuMu.screens;

import me.eluch.libgdx.DoJuMu.MenuHandler;
import me.eluch.libgdx.DoJuMu.MenuHandler.Handle;
import me.eluch.libgdx.DoJuMu.MenuHandler.Layout;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.PopupForString;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.network.ConnectionStatus;
import me.eluch.libgdx.DoJuMu.network.ServerMenuListHandler;
import me.eluch.libgdx.DoJuMu.network.client.Client;
import me.eluch.libgdx.DoJuMu.network.discover.UdpDiscover;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JoinScreen implements Screen {

	private static final String ipRegex = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
	private static final String hostRegex = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private MenuHandler menu;

	private int dividedWidth;
	private int dividedHeight;

	private ServerMenuListHandler serverList;
	private PopupForString ipPopup;

	private Client client;

	private final Game game;

	public JoinScreen(final Game game, final OrthographicCamera camera, final SpriteBatch batch) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;

		dividedWidth = (int) (camera.viewportWidth / Res._pattern.getWidth()) + 1;
		dividedHeight = (int) (camera.viewportHeight / Res._pattern.getHeight()) + 1;

		serverList = new ServerMenuListHandler(110, 410, 760, 350);
		ipPopup = new PopupForString();

		menu = new MenuHandler(Layout.Horizonal, Handle.KeyboardAndMouse, 10, (int) (camera.viewportHeight - Res._logo.getHeight() - 100));
		menu.addMenuItem("Join Selected", Res._button, Res._buttonFont, () -> {
			ipPopup.setValForced(serverList.getSelectedIp());
		});
		menu.addMenuItem("Join by IP", Res._button, Res._buttonFont, () -> {
			ipPopup.call("Enter that IP what you want to connect to:", serverList.getSelectedIp());
		});
		menu.addMenuItem("Search on LAN", Res._button, Res._buttonFont, () -> {
			serverList.clearServerItems();
			new UdpDiscover(16160, serverList).run();
		});
		menu.addMenuItem("Back", Res._button, Res._buttonFont, () -> {
			if (client != null) {
				client.stop();
			}
			game.setScreen(new MainMenuScreen(game, camera, batch));
			if (client != null) {
				client.stop();
			}
		});

		Client.setErrorMsg(null);
	}

	private void update(float delta) {

		if (client != null) {
			if (client.getConnectionStatus() == ConnectionStatus.ERROR) {
				client.stop();
			} else if (client.getConnectionStatus() == ConnectionStatus.CONNECTED) {
				game.setScreen(new LobbyScreen(game, camera, batch, client));
			}
		}

		String host = ipPopup.getWalueIfAwailable();
		if (host.length() > 0) {
			System.out.println("GOT IP TO CONNECT TO: " + host);
			if (host.matches(ipRegex) || host.matches(hostRegex)) {
				// MATCHES!!
				client = new Client(game, camera, batch, host, Options.SERVER_PORT, true);

			} else
				System.out.println("Wrong IP Address");
		}
		menu.handle(camera, Options.ScreenRes.width, Options.ScreenRes.height);
		serverList.handle(camera, Options.ScreenRes.width, Options.ScreenRes.height);
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
			batch.draw(Res._pattern.getTexture(), 0, 0, Res._pattern.getWidth() * dividedWidth, Res._pattern.getHeight() * dividedHeight, 0, dividedHeight, dividedWidth, 0);
			batch.draw(Res._logo.getTexture(), camera.viewportWidth / 2 - Res._logo.getWidth() / 2, camera.viewportHeight - Res._logo.getHeight() - 20);
			menu.draw(batch);
			Res._buttonFont.drawLeft(batch, "Host name", 100, 400);
			Res._buttonFont.drawLeft(batch, "IP Address", 400, 400);
			Res._buttonFont.drawLeft(batch, "Players", 750, 400);
			batch.draw(Res._transpartentPixel.getTexture(), 100, 390, camera.viewportWidth - 200, -350);
			Res._startScreenFont.drawCenter(batch, Client.getErrorMsg(), camera.viewportWidth / 2, 450);
			serverList.draw(batch);
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
