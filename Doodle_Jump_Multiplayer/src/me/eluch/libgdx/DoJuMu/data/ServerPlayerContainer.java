package me.eluch.libgdx.DoJuMu.data;

import io.netty.channel.Channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import me.eluch.libgdx.DoJuMu.Options;

public class ServerPlayerContainer extends CorePlayerContainer<ServerPlayer> {

	public ServerPlayerContainer() {
		super();
		ServerPlayer me = new ServerPlayer(Options.getHash());
		me.setName(Options.getName());
		me.setId(getAvailableMinId());
		me.setPing(0);
		try {
			me.setIp(new InetSocketAddress(InetAddress.getLocalHost(), Options.SERVER_PORT));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			me.setIp(new InetSocketAddress(InetAddress.getLoopbackAddress(), Options.SERVER_PORT));
		}
		addPlayer(me);
		setMySelf(me);
	}

	public boolean isHashContained(String hash) {
		boolean contains = false;
		for (ServerPlayer player : players) {
			if (player.getHash().equals(hash)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	public int getAvailableMinId() {
		int minid = 0;
		boolean found;
		do {
			found = false;
			for (ServerPlayer player : players) {
				if (player.id == minid) {
					found = true;
					break;
				}
			}
			if (found)
				minid++;
		} while (found);
		return minid;
	}

	public ServerPlayer getPlayerByChannel(Channel channel) {
		for (ServerPlayer player : players) {
			if (player.getChannelToPlayer() == channel) {
				return player;
			}
		}
		return null;
	}

	public ServerPlayer getPlayerByIP(InetSocketAddress ip) {
		for (ServerPlayer player : players) {
			if (player.getIp().toString().replaceFirst(".*[/]", "").equals(ip.toString().replaceFirst(".*[/]", ""))) {
				return player;
			}
		}
		return null;
	}

}
