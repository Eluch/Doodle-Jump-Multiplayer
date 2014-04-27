package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;

public class ServerItem {

	public final String hoster;
	public final String ip;
	public final String players;

	public ServerItem(String hoster, String ip, String players) {
		this.hoster = hoster;
		this.ip = ip;
		this.players = players;
	}

	public static ByteBuf encode(String hoster, String ip, String players) {
		WriteOnlyPacket oPacket = new WriteOnlyPacket(PacketType.SERVER_DATAS);
		oPacket.writeString(hoster);
		oPacket.writeString(ip);
		oPacket.writeString(players);
		return oPacket.getByteBuf();
	}

	public static ServerItem decode(ReadOnlyPacket packet) {
		String hoster = packet.readString();
		String ip = packet.readString();
		String players = packet.readString();
		return new ServerItem(hoster, ip, players);
	}
}
