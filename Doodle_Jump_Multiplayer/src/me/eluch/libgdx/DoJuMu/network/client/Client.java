package me.eluch.libgdx.DoJuMu.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.data.CorePlayerContainer;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.network.ConnectionStatus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Client {

	private final TcpClient tcp;
	private final UdpClient udp;
	private ConnectionStatus connectionStatus = ConnectionStatus.NOT_CONNECTED;

	private final Game game;
	private final OrthographicCamera camera;
	private final SpriteBatch batch;

	private final CorePlayerContainer<CorePlayer> players;
	private final ArrayList<Floor> floorBuffer = new ArrayList<>();

	private static String errorMsg = null;

	public Client(Game game, OrthographicCamera camera, SpriteBatch batch, String host, int port, boolean startNow) {
		this.game = game;
		this.camera = camera;
		this.batch = batch;
		players = new CorePlayerContainer<CorePlayer>();
		tcp = new TcpClient(host, this);
		udp = new UdpClient(this);
		if (startNow)
			start();
	}

	public void start() {
		floorBuffer.clear();
		if (!tcp.thread.isAlive())
			tcp.thread.start();
		if (!udp.thread.isAlive())
			udp.thread.start();
	}

	public void stop() {
		if (tcp.thread.isAlive())
			tcp.stopServer();
		stopUDP();
		connectionStatus = ConnectionStatus.NOT_CONNECTED;
		floorBuffer.clear();
	}

	public void stopUDP() {
		if (udp.thread.isAlive())
			udp.stopServer();
	}

	public Channel getTcpChannel() {
		return tcp.getChannel();
	}

	public Channel getUdpChannel() {
		return udp.getChannel();
	}

	public void sendWithTCP(ByteBuf byteBuf) {
		getTcpChannel().writeAndFlush(byteBuf);
	}

	public void sendWithUDP(ByteBuf byteBuf) {
		getUdpChannel().writeAndFlush(new DatagramPacket(byteBuf, (InetSocketAddress) getTcpChannel().remoteAddress()));
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

	public ArrayList<Floor> getFloorBuffer() {
		return floorBuffer;
	}
}
