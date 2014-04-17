package me.eluch.libgdx.DoJuMu.network.packets;

public class DoodleDatas {
	public final float x;
	public final float y;
	public final boolean facingRight;
	public final boolean jumping;

	public DoodleDatas(float x, float y, boolean facingRight, boolean jumping) {
		this.x = x;
		this.y = y;
		this.facingRight = facingRight;
		this.jumping = jumping;
	}
}
