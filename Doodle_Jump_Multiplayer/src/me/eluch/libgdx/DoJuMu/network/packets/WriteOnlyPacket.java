package me.eluch.libgdx.DoJuMu.network.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class WriteOnlyPacket {

	private ByteBuf buf;
	private final PacketType type;

	public WriteOnlyPacket(PacketType dataPacketType) {
		buf = Unpooled.buffer();
		type = dataPacketType;
		buf.writeInt(type.ordinal());
	}

	public PacketType getType() {
		return type;
	}

	public void writeInt(int value) {
		buf.writeInt(value);
	}

	public void writeLong(long value) {
		buf.writeLong(value);
	}

	public void writeFloat(float value) {
		buf.writeFloat(value);
	}

	public void writeBoolean(boolean value) {
		buf.writeBoolean(value);
	}

	public void writeString(String value) {
		buf.writeInt(value.length());
		for (int i = 0; i < value.length(); i++) {
			buf.writeChar(value.charAt(i));
		}
	}

	public void sendTo(Channel channel) {
		channel.writeAndFlush(buf);
		buf = null;
	}

	public ByteBuf getByteBuf() {
		return buf;
		// return buf.copy();
	}
}
