package me.eluch.libgdx.DoJuMu.network.packets;

public class DoodleDatasE extends DoodleDatas {

	public final boolean alive;

	public DoodleDatasE(float x, float y, float maxHeight, boolean facingRight, boolean jumping, boolean alive) {
		super(x, y, maxHeight, facingRight, jumping);
		this.alive = alive;
	}

	public DoodleDatasE(DoodleDatas datas, boolean alive) {
		super(datas.x, datas.y, datas.maxHeight, datas.facingRight, datas.jumping);
		this.alive = alive;
	}

}
