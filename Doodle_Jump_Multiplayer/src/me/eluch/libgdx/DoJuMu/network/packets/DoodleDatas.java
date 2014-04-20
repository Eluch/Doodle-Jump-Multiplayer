package me.eluch.libgdx.DoJuMu.network.packets;

public class DoodleDatas {
	public final float x;
	public final float y;
	public final float maxHeight;
	public final boolean facingRight;
	public final boolean jumping;

	public DoodleDatas(float x, float y, float maxHeight, boolean facingRight, boolean jumping) {
		this.x = x;
		this.y = y;
		this.maxHeight = maxHeight;
		this.facingRight = facingRight;
		this.jumping = jumping;
	}
}
