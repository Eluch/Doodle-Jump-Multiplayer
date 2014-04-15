package me.eluch.libgdx.DoJuMu.network.discover;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.network.ServerMenuListHandler;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

public class UdpDiscover {

	private final ServerMenuListHandler serverMenuListHandler;

	private final int port;

	public UdpDiscover(int port, ServerMenuListHandler serverMenuListHandler) {
		this.port = port;
		this.serverMenuListHandler = serverMenuListHandler;
	}

	public void run() {
		System.out.println("Server Discover starting...");
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true).handler(new UdpDiscoverHandler(serverMenuListHandler));

			Channel ch = b.bind(0).sync().channel();

			WriteOnlyPacket oPacket = new WriteOnlyPacket(PacketType.DISCOVER_BROADCAST);
			ch.writeAndFlush(new DatagramPacket(oPacket.getByteBuf(), new InetSocketAddress("255.255.255.255", port))).sync();

			if (!ch.closeFuture().await(Options.DISCOVER_TIMEOUT)) {
				group.shutdownGracefully();
			}

			ch.closeFuture().await();
			System.out.println("Server discover done.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

}
