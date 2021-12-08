package com.basmilius.time.network.codec;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ClientMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder
{

	@Override
	protected void decode(final ChannelHandlerContext channelHandlerContext, ByteBuf in, final List<Object> messages) throws Exception
	{
		try
		{
			if (in.readableBytes() < 4)
				return;

			ByteBuf buffer = in.duplicate();
			String string = new String(buffer.readBytes(buffer.readableBytes()).array());

			if (string.equalsIgnoreCase("<policy-file-request/>\0"))
			{
				channelHandlerContext.channel().writeAndFlush("<?xml version=\"1.0\"?><!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\"><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>\0").sync();
			}
			else
			{
				final SocketConnection client = Bootstrap.getEngine().getServer().getClientManager().getConnection(channelHandlerContext.channel());
				
				if (client == null)
					return;

				if (client.getEncryption().isInitialized())
				{
					byte[] bytes = new byte[in.readableBytes()];
					in.readBytes(bytes);
					bytes = client.getEncryption().getArc4().decrypt(bytes);
					in.setBytes(0, bytes);
					in.readerIndex(0);
				}
				
				int i = 0;
				while (in.readableBytes() >= 6 && i < 5)
				{
					i++;
					ByteBuf b = in.readBytes(in.readInt()).retain();

					short messageId = b.readShort();
					if (messageId > 4000 || messageId < 0)
						continue;

					messages.add(new ClientMessage(messageId, b));
				}
			}
		}
		catch (Exception ignored)
		{
			ignored.printStackTrace();
		}
	}

}
