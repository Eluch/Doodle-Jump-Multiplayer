package me.eluch.libgdx.DoJuMu.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import me.eluch.libgdx.DoJuMu.network.NetworkThread;

public class TcpServer implements Runnable, NetworkThread {

	private int port;

	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;

	public final Thread thread;
	
	private final Server server;

	public TcpServer(int port, Server server) {
		this.port = port;
		this.server = server;
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		System.out.println("TCP server starting...");
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TcpServerHandler(server));
				}
			}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture f = b.bind(port).sync();

			channel = f.channel();

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		System.out.println("TCP server stopped.");
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public void stopServer() {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
