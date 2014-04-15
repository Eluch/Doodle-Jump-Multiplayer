package me.eluch.libgdx.DoJuMu.data;

public class CorePlayer {

	protected int id;
	protected String name;
	protected long ping;

	public CorePlayer() {
	}

	public CorePlayer(int id, String name) {
		this.setId(id);
		this.setName(name);
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

	public long getPing() {
		return ping;
	}

	public void setPing(long ping) {
		this.ping = ping;
	}

}
