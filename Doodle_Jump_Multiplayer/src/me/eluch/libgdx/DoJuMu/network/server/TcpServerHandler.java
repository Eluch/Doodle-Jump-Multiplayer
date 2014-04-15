package me.eluch.libgdx.DoJuMu.network.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.eluch.libgdx.DoJuMu.GameState;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.network.ConnectionStatus;
import me.eluch.libgdx.DoJuMu.network.packets.AllPlayers;
import me.eluch.libgdx.DoJuMu.network.packets.OnePlayerConnected;
import me.eluch.libgdx.DoJuMu.network.packets.OnePlayerDisconnected;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.Validation;
import me.eluch.libgdx.DoJuMu.network.packets.ValidationDatas;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

public class TcpServerHandler extends ChannelInboundHandlerAdapter {

	private final Server server;

	public TcpServerHandler(Server server) {
		this.server = server;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client try to connecting with: " + ctx.channel().remoteAddress().toString());
		if (server.getPlayers().getNumberOfPlayers() >= Options.MAXPLAYERS) {
			WriteOnlyPacket op = new WriteOnlyPacket(PacketType.SERVER_IS_FULL);
			ctx.writeAndFlush(op.getByteBuf());
			ctx.close();
		} else {
			WriteOnlyPacket oPacket = new WriteOnlyPacket(PacketType.NEED_VALIDATION);
			ctx.writeAndFlush(oPacket.getByteBuf());
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
		if (msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf) msg;
			ReadOnlyPacket iPacket = new ReadOnlyPacket(buf);
			switch (iPacket.getType()) {
			case VALIDATING:
				ValidationDatas datas = Validation.decodeStep1(iPacket);
				if (datas.version != Options.VERSION) {
					WriteOnlyPacket op = new WriteOnlyPacket(PacketType.WRONG_VERSION);
					ctx.writeAndFlush(op.getByteBuf());
					ctx.close();
				} else if (server.getPlayers().isNameContained(datas.name)) {
					/*
					 * if (server.getPlayers().isHashContained(datas.hash)) {
					 * //TODO Reconnect player! } else
					 */{
						WriteOnlyPacket op = new WriteOnlyPacket(PacketType.NAME_IN_USE);
						ctx.writeAndFlush(op.getByteBuf());
						ctx.close();
					}
				} else {
					// Add to players id_general - eltarol - kikuld
					ServerPlayer player = new ServerPlayer(datas.hash, server.getPlayers().getAvailableMinId(), datas.name, ctx.channel());
					server.sendToAllPlayersWithTCP(OnePlayerConnected.encode(player.getId(), player.getName()));
					server.getPlayers().addPlayer(player);
					player.setConnectionStatus(ConnectionStatus.CONNECTED);
					ctx.writeAndFlush(Validation.encodeStep2(player.getId()));
				}
				break;
			case REQUEST_ALL_PLAYERS:
				ctx.writeAndFlush(AllPlayers.encode(server.getPlayers()));
				break;
			default:
				break;
			}

		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) {
		ServerPlayer player = server.getPlayers().getPlayerByChannel(ctx.channel());
		if (player != null) {
			if (server.getGameState() == GameState.LOBBY) {
				server.getPlayers().removePlayer(player);
				server.sendToAllPlayersWithTCP(OnePlayerDisconnected.encode(player.getId()));
				// TODO Send to all client that one of them dcd..
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.err.println(cause.getMessage());
	}

}
