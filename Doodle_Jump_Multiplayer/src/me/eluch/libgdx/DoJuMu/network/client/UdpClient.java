package me.eluch.libgdx.DoJuMu.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.network.NetworkThread;

public class UdpClient implements Runnable, NetworkThread {

	EventLoopGroup group;
	Channel channel;

	public final Thread thread;

	private final Client client;

	public UdpClient(Client client) {
		this.client = client;
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new UdpClientHandler(client));

			channel = b.bind(Options.CLIENT_PORT).sync().channel();

			channel.closeFuture().await();
		} catch (Exception e) {
			e.printStackTrace();
			client.stop();
		} finally {
			group.shutdownGracefully();
		}
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public void stopServer() {
		group.shutdownGracefully();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
