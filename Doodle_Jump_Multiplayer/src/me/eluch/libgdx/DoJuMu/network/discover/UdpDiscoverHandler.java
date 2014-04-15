package me.eluch.libgdx.DoJuMu.network.discover;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import me.eluch.libgdx.DoJuMu.network.ServerMenuListHandler;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.ServerItem;

public class UdpDiscoverHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private ServerMenuListHandler serverMenuListHandler;

	public UdpDiscoverHandler(ServerMenuListHandler serverMenuListHandler) {
		this.serverMenuListHandler = serverMenuListHandler;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
		ReadOnlyPacket iPacket = new ReadOnlyPacket(msg.content());

		if (iPacket.getType() == PacketType.SERVER_DATAS) {
			ServerItem item = ServerItem.decode(iPacket);
			System.out.println("Found server at ip: " + item.ip);
			serverMenuListHandler.addServerItem(item);
		} else {
			System.err.println("Got invalid answer from: " + ctx.channel().remoteAddress().toString());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}