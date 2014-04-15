package me.eluch.libgdx.DoJuMu.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.network.NetworkThread;

public class TcpClient implements Runnable, NetworkThread {

	private String host;

	EventLoopGroup workerGroup;
	private Channel channel;

	public final Thread thread;
	
	private final Client client;

	public TcpClient(String host, Client client) {
		this.client = client;
		this.host = host;
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		System.out.println("Client starting...");
		workerGroup = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new TcpClientHandler(client));
				}
			});

			ChannelFuture f = b.connect(host, Options.SERVER_PORT).sync();

			channel = f.channel();

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
			client.stop();
		} finally {
			workerGroup.shutdownGracefully();
		}
		System.out.println("Client stopped.");
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public void stopServer() {
		workerGroup.shutdownGracefully();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
