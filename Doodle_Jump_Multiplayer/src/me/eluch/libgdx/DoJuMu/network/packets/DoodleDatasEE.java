package me.eluch.libgdx.DoJuMu.network.packets;

public class DoodleDatasEE extends DoodleDatasE {

	public final int id;

	public DoodleDatasEE(float x, float y, boolean facingRight, boolean jumping, boolean alive, int id) {
		super(x, y, facingRight, jumping, alive);
		this.id = id;
	}

	public DoodleDatasEE(DoodleDatasE datas, int id) {
		super(datas.x, datas.y, datas.facingRight, datas.jumping, datas.alive);
		this.id = id;
	}

}
