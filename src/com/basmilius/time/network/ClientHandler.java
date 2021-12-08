package com.basmilius.time.network;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ClientMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

class ClientHandler extends ChannelHandlerAdapter implements ChannelHandler
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		final SocketConnection connection = Bootstrap.getEngine().getServer().getClientManager().getConnection(ctx.channel());

		if (connection == null)
			return;

		if (ClientMessage.class.isInstance(msg))
		{
			final ClientMessage message = (ClientMessage) msg;

			connection.progressReceivedData(message);

			message.getMessageBuffer().release();
		}

		connection.getSocket().getContext().fireChannelReadComplete();
	}

	@Override
	public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception
	{
		if (!Bootstrap.getEngine().getServer().getClientManager().addClient(channelHandlerContext))
		{
			channelHandlerContext.channel().disconnect();
		}
	}

	@Override
	public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception
	{
		if (Bootstrap.getEngine().getServer().getClientManager().containsClient(channelHandlerContext.channel()))
		{
			Bootstrap.getEngine().getServer().getClientManager().disposeClient(channelHandlerContext.channel());
		}
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable throwable) throws Exception
	{
		throwable.printStackTrace();

		if (Bootstrap.getEngine().getServer().getClientManager().containsClient(channelHandlerContext.channel()))
		{
			Bootstrap.getEngine().getServer().getClientManager().disposeClient(channelHandlerContext.channel());
		}
	}
}