package me.eluch.libgdx.DoJuMu.network.packets;

import me.eluch.libgdx.DoJuMu.Options;
import me.eluch.libgdx.DoJuMu.game.doodle.DoodleGenderType;
import io.netty.buffer.ByteBuf;

public abstract class Validation {

	public static ByteBuf encodeStep1() {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.VALIDATING);
		op.writeString(Options.getHash());
		op.writeString(Options.VERSION);
		op.writeString(Options.getName() + (Options.DEBUG ? "_D" : "")); //TODO NOTE DEBUG
		op.writeInt(Options.getCharacter().ordinal());
		return op.getByteBuf();
	}

	public static ValidationDatas decodeStep1(ReadOnlyPacket readOnlyPacket) {
		String hash = readOnlyPacket.readString();
		String version = readOnlyPacket.readString();
		String name = readOnlyPacket.readString();
		DoodleGenderType genderType = DoodleGenderType.values()[readOnlyPacket.readInt()];
		return new ValidationDatas(hash, version, name, genderType);
	}

	public static ByteBuf encodeStep2(int id) {
		WriteOnlyPacket op = new WriteOnlyPacket(PacketType.ACCEPTED_VALIDATING);
		op.writeInt(id);
		return op.getByteBuf();
	}

	public static int decodeStep2(ReadOnlyPacket iPacket) {
		return iPacket.readInt();
	}
}
