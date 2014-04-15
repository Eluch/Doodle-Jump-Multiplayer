package me.eluch.libgdx.DoJuMu.data;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.network.ConnectionStatus;

public class ServerPlayer extends CorePlayer {

	private String hash;
	private Channel channelToPlayer;
	private InetSocketAddress ip;
	private ConnectionStatus connectionStatus;
	private long pingSent;
	private long pingReceived;

	public ServerPlayer(String hash) {
		this.hash = hash;
		this.pingSent = this.pingReceived = 0;
	}

	public ServerPlayer(String hash, int id, String name, Channel channel) {
		super(id, name);
		this.hash = hash;
		this.channelToPlayer = channel;
	}

	public String getHash() {
		return hash;
	}

	public Channel getChannelToPlayer() {
		return channelToPlayer;
	}

	public InetSocketAddress getIp() {
		if (ip == null)
			pharseIp();
		return ip;
	}

	public void setIp(InetSocketAddress ip) {
		this.ip = ip;
	}

	private void pharseIp() {

		String ips = channelToPlayer.remoteAddress().toString(); // "/10.200.100.206:49505"
		ips = ips.replaceFirst(".*[/]", "").replaceFirst("[:].*", "");
		ip = new InetSocketAddress(ips, Options.CLIENT_PORT);
	}

	public ConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(ConnectionStatus connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public long getPingSent() {
		return pingSent;
	}

	public void setPingSent(long pingSent) {
		this.pingSent = pingSent;
	}

	public long getPingReceived() {
		return pingReceived;
	}

	public void setPingReceived(long pingReceived) {
		this.pingReceived = pingReceived;
	}

}
