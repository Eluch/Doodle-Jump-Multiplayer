package me.eluch.libgdx.DoJuMu.network;

import io.netty.channel.Channel;

public interface NetworkThread {

	public Channel getChannel();

	public void stopServer();

}
