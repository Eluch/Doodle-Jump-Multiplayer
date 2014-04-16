package me.eluch.libgdx.DoJuMu.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.data.CorePlayer;
import me.eluch.libgdx.DoJuMu.game.GameRole;
import me.eluch.libgdx.DoJuMu.network.ConnectionStatus;
import me.eluch.libgdx.DoJuMu.network.packets.AllPlayers;
import me.eluch.libgdx.DoJuMu.network.packets.OnePlayerConnected;
import me.eluch.libgdx.DoJuMu.network.packets.OnePlayerDisconnected;
import me.eluch.libgdx.DoJuMu.network.packets.PingDatas;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.Validation;
import me.eluch.libgdx.DoJuMu.screens.GameScreen;

public class TcpClientHandler extends ChannelInboundHandlerAdapter {

	private final Client client;

	public TcpClientHandler(Client client) {
		this.client = client;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof ByteBuf) {
			ByteBuf buf = (ByteBuf) msg;
			ReadOnlyPacket iPacket = new ReadOnlyPacket(buf);

			switch (iPacket.getType()) {
			case NEED_VALIDATION:
				ctx.writeAndFlush(Validation.encodeStep1());
				break;
			case ACCEPTED_VALIDATING:
				CorePlayer player = new CorePlayer(Validation.decodeStep2(iPacket), Options.getName() + "_VALID", Options.getCharacter()); // TODO remove the suffix
				client.getPlayers().addPlayer(player);
				client.getPlayers().setMySelf(player);
				client.setConnectionStatus(ConnectionStatus.CONNECTED);
				break;
			case WRONG_VERSION:
				Client.setErrorMsg("The server has different version!");
				client.setConnectionStatus(ConnectionStatus.ERROR);
				break;
			case NAME_IN_USE:
				Client.setErrorMsg("Your name is already used on that server!");
				client.setConnectionStatus(ConnectionStatus.ERROR);
				break;
			case SERVER_IS_FULL:
				Client.setErrorMsg("Server is full!");
				client.setConnectionStatus(ConnectionStatus.ERROR);
				break;
			case RESPONSE_ALL_PLAYERS:
				AllPlayers.decode(iPacket, client.getPlayers());
				break;
			case PING_DATAS:
				PingDatas.decode(iPacket, client.getPlayers());
				break;
			case ONE_PLAYER_CONNECTED:
				CorePlayer newPlayer = OnePlayerConnected.decoode(iPacket);
				client.getPlayers().addPlayer(newPlayer);
				break;
			case ONE_PLAYER_DISCONNECTED:
				client.getPlayers().removeByID(OnePlayerDisconnected.decoode(iPacket));
				break;
			case GAME_STARTING:
				client.getGame().setScreen(new GameScreen(client.getGame(), client.getCamera(), client.getBatch(), GameRole.CLIENT, client));
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
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// cause.printStackTrace();
		System.err.println(cause.getMessage());
		ctx.close();
	}

}
