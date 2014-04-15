package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;
import me.eluch.libgdx.DoJuMu.data.ServerPlayer;
import me.eluch.libgdx.DoJuMu.network.server.Server;

public class Ping {

	private final Thread thread;

	private final Runnable runnable;

	private boolean needToRun;

	public Ping(final Server server) {
		this.runnable = new Runnable() {

			@Override
			public void run() {
				while (needToRun) {
					for (ServerPlayer player : server.getPlayers().getPlayers()) {
						if (player != server.getPlayers().getMySelf()) {
							WriteOnlyPacket op = new WriteOnlyPacket(PacketType.PING);
							server.getUdpChannel().writeAndFlush(new DatagramPacket(op.getByteBuf(), player.getIp()));
							player.setPingSent(System.currentTimeMillis());
						}
					}
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					for (ServerPlayer player : server.getPlayers().getPlayers()) {
						if (player != server.getPlayers().getMySelf()) {
							if (player.getPingReceived() > player.getPingSent())
								player.setPing(player.getPingReceived() - player.getPingSent());
							else
								player.setPing(-1);
						}
					}

					ByteBuf data = PingDatas.encode(server.getPlayers());
					for (ServerPlayer player : server.getPlayers().getPlayers()) {
						if (player != server.getPlayers().getMySelf()) {
							player.getChannelToPlayer().writeAndFlush(data.copy());
						}
					}
				}
			}
		};
		this.thread = new Thread(runnable);
	}

	public void start() {
		System.out.println("Pinger thread started.");
		needToRun = true;
		if (!this.thread.isAlive())
			this.thread.start();
	}

	public void stop() {
		System.out.println("Pinger thread stopped.");
		needToRun = false;
		try {
			if (this.thread.isAlive())
				this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
