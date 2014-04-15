package me.eluch.libgdx.DoJuMu.network.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.ServerItem;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private final Server server;

	public UdpServerHandler(Server server) {
		this.server = server;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws UnknownHostException, InterruptedException {

		ReadOnlyPacket iPacket = new ReadOnlyPacket(msg.content());

		switch (iPacket.getType()) {
		case DISCOVER_BROADCAST:
			System.out.println("Broadcast request income.");
			ctx.writeAndFlush(new DatagramPacket(ServerItem.encode(Options.getName(), InetAddress.getLocalHost().getHostAddress().toString(), server.getPlayers().getNumberOfPlayers() + "/" + Options.MAXPLAYERS), msg.sender())).sync();
			break;
		case PONG:
			ServerPlayer player = server.getPlayers().getPlayerByIP(msg.sender());
			if (player != null) {
				player.setPingReceived(System.currentTimeMillis());
			}
			break;
		default:
			System.err.println("Invalid packet!");
			break;
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}
	//
}
