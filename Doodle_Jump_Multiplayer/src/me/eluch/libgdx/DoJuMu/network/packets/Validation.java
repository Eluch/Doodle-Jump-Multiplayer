package me.eluch.libgdx.DoJuMu.network.packets;

import me.eluch.libgdx.DoJuMu.Options;
import io.netty.buffer.ByteBuf;

public abstract class Validation {

	public static ByteBuf encodeStep1() {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.VALIDATING);
		op.writeString(Options.getHash());
		op.writeInt(Options.VERSION);
		op.writeString(Options.getName() + "_VALID"); //TODO remove the suffix
		return op.getByteBuf();
	}

	public static ValidationDatas decodeStep1(ReadOnlyPacket readOnlyPacket) {
		String hash = readOnlyPacket.readString();
		int version = readOnlyPacket.readInt();
		String name = readOnlyPacket.readString();
		return new ValidationDatas(hash, version, name);
	}
	
	public static ByteBuf encodeStep2(int id){
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.ACCEPTED_VALIDATING);
		op.writeInt(id);
		return op.getByteBuf();
	}
	
	public static int decodeStep2(ReadOnlyPacket iPacket){
		return iPacket.readInt();
	}

}
