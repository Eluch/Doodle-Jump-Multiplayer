package me.eluch.libgdx.DoJuMu.network.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import me.eluch.libgdx.DoJuMu.network.NetworkThread;

public class UdpServer implements Runnable, NetworkThread {

	private final int port;

	private EventLoopGroup group;
	private Channel channel;

	public final Thread thread;
	
	private final Server server;

	public UdpServer(int port, Server server) {
		this.port = port;
		this.server = server;
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		System.out.println("UDP Server starting...");
		group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new UdpServerHandler(server));

			channel = b.bind(port).sync().channel();
			channel.closeFuture().await();
			System.out.println("UDP Server stopped.");
		} catch (Exception e) {
			e.printStackTrace();
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
