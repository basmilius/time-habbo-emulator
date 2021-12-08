package com.basmilius.time.network.codec;

import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.ServerMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Object>
{

	@Override
	protected final void encode(final ChannelHandlerContext channelHandlerContext, final Object msg, final ByteBuf byteBuf) throws Exception
	{
		if (String.class.isInstance(msg))
		{
			byteBuf.writeBytes(msg.toString().getBytes());
		}
		else if (QueuedComposers.class.isInstance(msg))
		{
			QueuedComposers message = ((QueuedComposers) msg);
			byteBuf.writeBytes(message.getBytes());
			message.dispose();
		}
		else if (ServerMessage.class.isInstance(msg))
		{
			ByteBuf message = ((ServerMessage) msg).get();
			byteBuf.writeBytes(message);
			message.release();
		}

		channelHandlerContext.flush();
	}

}
