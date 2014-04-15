package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;

public class ReadOnlyPacket {

	private ByteBuf buf;
	private final PacketType type;

	public ReadOnlyPacket(ByteBuf byteBuf) {
		buf = byteBuf;
		int ordinal = buf.readInt();
		PacketType packetType;
		try {
			packetType = PacketType.values()[ordinal];
		} catch (ArrayIndexOutOfBoundsException e) {
			packetType = PacketType.UNKNOWN;
		}
		type = packetType;
	}

	public PacketType getType() {
		return type;
	}

	public int readInt() {
		return buf.readInt();
	}

	public long readLong() {
		return buf.readLong();
	}

	public float readFloat() {
		return buf.readFloat();
	}

	public boolean readBoolean() {
		return buf.readBoolean();
	}

	public String readString() {
		int strlen = buf.readInt();
		String s = "";
		for (int i = 0; i < strlen; i++) {
			s += buf.readChar();
		}
		return s;
	}

}
