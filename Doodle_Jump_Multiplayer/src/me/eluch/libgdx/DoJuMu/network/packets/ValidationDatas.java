package me.eluch.libgdx.DoJuMu.network.packets;

import me.eluch.libgdx.DoJuMu.game.doodle.DoodleGenderType;

public class ValidationDatas {
	
	public final String hash;
	public final String version;
	public final String name;
	public final DoodleGenderType genderType;
	
	public ValidationDatas(String hash, String version, String name, DoodleGenderType genderType) {
		this.hash = hash;
		this.version = version;
		this.name = name;
		this.genderType = genderType;
	}
}
