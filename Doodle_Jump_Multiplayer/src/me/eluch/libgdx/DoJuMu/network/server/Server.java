package me.eluch.libgdx.DoJuMu.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import me.eluch.libgdx.DoJuMu.GameState;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.data.ServerPlayerContainer;
import me.eluch.libgdx.DoJuMu.network.packets.Ping;

public class Server {

	private final TcpServer tcp;
	private final UdpServer udp;

	private final Ping ping;

	private final ServerPlayerContainer players;

	private GameState gameState;

	public Server(int port, boolean startNow) {
		gameState = GameState.LOBBY;
		players = new ServerPlayerContainer();
		tcp = new TcpServer(port, this);
		udp = new UdpServer(port, this);
		ping = new Ping(this);
		if (startNow)
			start();
	}

	public void start() {
		if (!tcp.thread.isAlive())
			tcp.thread.start();
		if (!udp.thread.isAlive())
			udp.thread.start();
		ping.start();
	}

	public void stop() {
		ping.stop();
		if (tcp.thread.isAlive())
			tcp.stopServer();
		if (udp.thread.isAlive())
			udp.stopServer();
	}

	public Channel getTcpChannel() {
		return tcp.getChannel();
	}

	public Channel getUdpChannel() {
		return udp.getChannel();
	}

	public ServerPlayerContainer getPlayers() {
		return players;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public void sendToAllPlayersWithTCP(ByteBuf byteBuf) { //except the server
		for (ServerPlayer player : players.getPlayers()) {
			if (player != players.getMySelf()) {
				player.getChannelToPlayer().writeAndFlush(byteBuf);
			}
		}
	}

	public void sendToAllPlayersWithUDP(ByteBuf byteBuf) { //except the server
		for (ServerPlayer player : players.getPlayers()) {
			if (player != players.getMySelf()) {
				getUdpChannel().writeAndFlush(new DatagramPacket(byteBuf, player.getIp()));
			}
		}
	}

}
