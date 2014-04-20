package me.eluch.libgdx.DoJuMu.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public final class Codec extends ByteToMessageCodec<ByteBuf> {

	private static final int SPEC_ID = 2164;

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {
		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(SPEC_ID);
		buf.writeInt(msg.writerIndex());
		buf.writeBytes(msg);

		out.writeBytes(buf);
	}

	private int sid = -1; //Special ID
	private int length = -1;
	private State state = State.AWAIT_SID;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
		if (state == State.AWAIT_SID) {
			if (in.readableBytes() < Integer.BYTES)
				return;

			sid = in.readInt();
			if (sid != SPEC_ID) {
				System.err.println("Wrong Special ID in decoder!");
				ctx.close();
				in.release();
				return;
			}
			state = State.AWAIT_LEN;
		}

		if (state == State.AWAIT_LEN) {
			if (in.readableBytes() < Integer.BYTES)
				return;
			length = in.readInt();
			state = State.AWAIT_DATA;
		}

		if (state == State.AWAIT_DATA) {
			if (in.readableBytes() < length)
				return;
			out.add(in.readBytes(length));
			sid = -1;
			length = -1;
			state = State.AWAIT_SID;
		}
	}

	private enum State {
		AWAIT_SID, AWAIT_LEN, AWAIT_DATA,
	}
}
