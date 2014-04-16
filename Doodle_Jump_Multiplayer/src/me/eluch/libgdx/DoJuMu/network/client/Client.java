package me.eluch.libgdx.DoJuMu.network.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.netty.channel.Channel;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.network.ConnectionStatus;

public class Client {

	private final TcpClient tcp;
	private final UdpClient udp;
	private ConnectionStatus connectionStatus;

	private final Game game;
	private final OrthographicCamera camera;
	private final SpriteBatch batch;

	private final CorePlayerContainer<CorePlayer> players;

	private static String errorMsg;

	public Client(Game game, OrthographicCamera camera, SpriteBatch batch, String host, int port, boolean startNow) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;
		Client.errorMsg = null;
		this.connectionStatus = ConnectionStatus.NOT_CONNECTED;
		players = new CorePlayerContainer<CorePlayer>();
		tcp = new TcpClient(host, this);
		udp = new UdpClient(this);
		if (startNow)
			start();
	}

	public void start() {
		if (!tcp.thread.isAlive())
			tcp.thread.start();
		if (!udp.thread.isAlive())
			udp.thread.start();
	}

	public void stop() {
		if (tcp.thread.isAlive())
			tcp.stopServer();
		if (udp.thread.isAlive())
			udp.stopServer();
		connectionStatus = ConnectionStatus.NOT_CONNECTED;
	}

	public Channel getTcpChannel() {
		return tcp.getChannel();
	}

	public Channel getUdpChannel() {
		return udp.getChannel();
	}

	public static String getErrorMsg() {
		if (errorMsg == null)
			return "";
		return errorMsg;
	}

	public static void setErrorMsg(String errorMsg) {
		Client.errorMsg = errorMsg;
	}

	public ConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(ConnectionStatus connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public CorePlayerContainer<CorePlayer> getPlayers() {
		return players;
	}

	public boolean isRunning() {
		return tcp.thread.isAlive();
	}

	public Game getGame() {
		return game;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
