package me.eluch.libgdx.DoJuMu.data;

import me.eluch.libgdx.DoJuMu.game.doodle.DoodleGenderType;

public class CorePlayer {

	protected int id;
	protected String name;
	protected DoodleGenderType genderType;
	protected long ping;

	public CorePlayer() {
	}

	public CorePlayer(int id, String name, DoodleGenderType genderType) {
		this.setId(id);
		this.setName(name);
		this.setGenderType(genderType);
		this.ping = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public DoodleGenderType getGenderType() {
		return genderType;
	}

	public void setGenderType(DoodleGenderType genderType) {
		this.genderType = genderType;
	}

	public long getPing() {
		return ping;
	}

	public void setPing(long ping) {
		this.ping = ping;
	}

}
