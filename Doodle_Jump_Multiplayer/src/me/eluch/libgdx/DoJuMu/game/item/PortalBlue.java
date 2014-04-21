package me.eluch.libgdx.DoJuMu.game.item;

import io.netty.buffer.ByteBuf;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.gfx.AnimatedImage;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

public class PortalBlue extends Item {

	AnimatedImage texture;

	public PortalBlue(Floor bindedFloor) {
		super(bindedFloor);
		this.texture = Res._portal_blue;
		this.rec = new Rectangle(bindedFloor.getRec().x, bindedFloor.getRec().y, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void updatePos() {
		this.rec.x = bindedFloor.getRec().x + bindedFloor.getRec().width / 2 - this.getRec().width / 2;
		this.rec.y = bindedFloor.getRec().y + bindedFloor.getRec().height;
	}

	@Override
	protected void itemHitDoodle() {
	}

	@Override
	protected void itemHitDoodleFoot() {
		effect = Effect.PORTALLING;
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(texture.getNextImage(), rec.x, rec.y - scrR.y);
	}
	
	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.NEW_ITEM);
		p.writeInt(ItemType.PORTAL.ordinal());
		return p.getByteBuf();
	}

	public static Item decode(ReadOnlyPacket p, Floor bindedFloor) {
		return new PortalBlue(bindedFloor);
	}

}
