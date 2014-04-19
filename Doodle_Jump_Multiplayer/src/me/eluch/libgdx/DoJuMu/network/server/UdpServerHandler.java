package me.eluch.libgdx.DoJuMu.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleBasic;
import me.eluch.libgdx.DoJuMu.network.packets.DoodleDatas;
import me.eluch.libgdx.DoJuMu.network.packets.MyDoodleDatas;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.ServerItem;

public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private final Server server;

	public UdpServerHandler(Server server) {
		this.server = server;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws UnknownHostException, InterruptedException {

		ByteBuf buf = msg.content();
		ReadOnlyPacket iPacket = new ReadOnlyPacket(buf);

		switch (iPacket.getType()) {
		case DISCOVER_BROADCAST:
			System.out.println("Broadcast request income.");
			ctx.writeAndFlush(
					new DatagramPacket(ServerItem.encode(Options.getName(), InetAddress.getLocalHost().getHostAddress().toString(), server.getPlayers().getNumberOfPlayers() + "/"
							+ Options.MAXPLAYERS), msg.sender())).sync();
			break;
		case PONG:
			ServerPlayer player = server.getPlayers().getPlayerByIP(msg.sender());
			if (player != null) {
				player.setPingReceived(System.currentTimeMillis());
			}
			break;
		case MY_DOODLE_DATAS:
			DoodleBasic d = server.getPlayers().getPlayerByIP(msg.sender()).getDoodle();
			if (d.isAlive()) {
				DoodleDatas dd = MyDoodleDatas.decode(iPacket);
				d.setXY(dd.x, dd.y);
				d.setFacingRight(dd.facingRight);
				d.setJumping(dd.jumping);
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
