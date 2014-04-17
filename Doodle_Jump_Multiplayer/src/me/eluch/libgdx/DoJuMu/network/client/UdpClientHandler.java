package me.eluch.libgdx.DoJuMu.network.client;

import me.eluch.libgdx.DoJuMu.network.packets.AllDoodleDatas;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class UdpClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	private final Client client;

	public UdpClientHandler(Client client) {
		this.client = client;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
		ByteBuf buf = msg.content();
		ReadOnlyPacket rp = new ReadOnlyPacket(buf);
		switch (rp.getType()) {
		case PING:
			WriteOnlyPacket op = new WriteOnlyPacket(PacketType.PONG);
			ctx.writeAndFlush(new DatagramPacket(op.getByteBuf(), msg.sender()));
			break;
		case ALL_DOODLE_DATAS:
			AllDoodleDatas.decode(rp, client.getPlayers());
			break;
		default:
			break;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
