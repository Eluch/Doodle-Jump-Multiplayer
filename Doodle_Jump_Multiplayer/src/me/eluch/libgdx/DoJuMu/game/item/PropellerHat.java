package me.eluch.libgdx.DoJuMu.game.item;

import io.netty.buffer.ByteBuf;
import me.eluch.libgdx.DoJuMu.Res;
import me.eluch.libgdx.DoJuMu.game.Effect;
import me.eluch.libgdx.DoJuMu.game.floors.Floor;
import me.eluch.libgdx.DoJuMu.gfx.AnimatedImage;
import me.eluch.libgdx.DoJuMu.network.packets.PacketType;
import me.eluch.libgdx.DoJuMu.network.packets.ReadOnlyPacket;
import me.eluch.libgdx.DoJuMu.network.packets.WriteOnlyPacket;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PropellerHat extends Item {

	AnimatedImage texture;

	public PropellerHat(Floor bindedFloor) {
		super(bindedFloor);
		this.texture = Res._propellerhat;
		this.rec = new Rectangle(bindedFloor.getRec().x, bindedFloor.getRec().y, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void updatePos() {
		this.rec.x = bindedFloor.getRec().x + bindedFloor.getRec().width / 2 - this.getRec().width / 2;
		this.rec.y = bindedFloor.getRec().y + bindedFloor.getRec().height;
	}

	@Override
	protected void itemHitDoodle() {
		need2Show = false;
		effect = Effect.PROPELLER_HAT_EQUIPPING;
	}

	@Override
	protected void itemHitDoodleFoot() {
	}

	@Override
	protected void render(SpriteBatch batch, Rectangle scrR) {
		batch.draw(texture.getSpecificImage(0), rec.x, rec.y - scrR.y);
	}
	
	@Override
	public ByteBuf encode() {
		WriteOnlyPacket p = new WriteOnlyPacket(PacketType.NEW_ITEM);
		p.writeInt(ItemType.PROPELLER_HAT.ordinal());
		return p.getByteBuf();
	}

	public static Item decode(ReadOnlyPacket p, Floor bindedFloor) {
		return new PropellerHat(bindedFloor);
	}

}
