package me.eluch.libgdx.DoJuMu.network.packets;

public class ValidationDatas {
	
	public final String hash;
	public final int version;
	public final String name;
	
	public ValidationDatas(String hash, int version, String name) {
		this.hash = hash;
		this.version = version;
		this.name = name;
	}

}
