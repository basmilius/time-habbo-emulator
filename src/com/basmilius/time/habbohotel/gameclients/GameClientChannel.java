package com.basmilius.time.habbohotel.gameclients;

import com.basmilius.time.communication.connection.SocketConnection;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.net.SocketAddress;

public class GameClientChannel implements Channel
{

	private final ChannelHandlerContext context;
	private final SocketConnection connection;
	private final int id;

	public GameClientChannel(final int id, final ChannelHandlerContext context) throws Exception
	{
		this.context = context;
		this.connection = new SocketConnection(this);
		this.id = id;
	}

	public Channel getChannel()
	{
		return this.getContext().channel();
	}

	public ChannelHandlerContext getContext()
	{
		return this.context;
	}

	public SocketConnection getConnection ()
	{
		return this.connection;
	}

	public int getId()
	{
		return this.id;
	}

	@Override
	public ChannelId id()
	{
		return new ChannelId()
		{

			@Override
			public String asShortText()
			{
				return Integer.toString(GameClientChannel.this.id);
			}

			@Override
			public String asLongText()
			{
				return Integer.toString(GameClientChannel.this.id);
			}

			@Override
			public int compareTo(final ChannelId o)
			{
				return 0;
			}
		};
	}

	@Override
	public EventLoop eventLoop()
	{
		return this.getChannel().eventLoop();
	}

	@Override
	public Channel parent()
	{
		return this.getChannel().parent();
	}

	@Override
	public ChannelConfig config()
	{
		return this.getChannel().config();
	}

	@Override
	public boolean isOpen()
	{
		return this.getChannel().isOpen();
	}

	@Override
	public boolean isRegistered()
	{
		return this.getChannel().isRegistered();
	}

	@Override
	public boolean isActive()
	{
		return this.getChannel().isActive();
	}

	@Override
	public ChannelMetadata metadata()
	{
		return this.getChannel().metadata();
	}

	@Override
	public SocketAddress localAddress()
	{
		return this.getChannel().localAddress();
	}

	@Override
	public SocketAddress remoteAddress()
	{
		return this.getChannel().remoteAddress();
	}

	@Override
	public ChannelFuture closeFuture()
	{
		return this.getChannel().closeFuture();
	}

	@Override
	public boolean isWritable()
	{
		return this.getChannel().isWritable();
	}

	@Override
	public Unsafe unsafe()
	{
		return this.getChannel().unsafe();
	}

	@Override
	public ChannelPipeline pipeline()
	{
		return this.getChannel().pipeline();
	}

	@Override
	public ByteBufAllocator alloc()
	{
		return this.getChannel().alloc();
	}

	@Override
	public ChannelPromise newPromise()
	{
		return this.getChannel().newPromise();
	}

	@Override
	public ChannelProgressivePromise newProgressivePromise()
	{
		return this.getChannel().newProgressivePromise();
	}

	@Override
	public ChannelFuture newSucceededFuture()
	{
		return this.getChannel().newSucceededFuture();
	}

	@Override
	public ChannelFuture newFailedFuture(final Throwable throwable)
	{
		return this.getChannel().newFailedFuture(throwable);
	}

	@Override
	public ChannelPromise voidPromise()
	{
		return this.getChannel().voidPromise();
	}

	@Override
	public ChannelFuture bind(final SocketAddress socketAddress)
	{
		return this.getChannel().bind(socketAddress);
	}

	@Override
	public ChannelFuture connect(final SocketAddress socketAddress)
	{
		return this.getChannel().connect(socketAddress);
	}

	@Override
	public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2)
	{
		return this.getChannel().connect(socketAddress, socketAddress2);
	}

	@Override
	public ChannelFuture disconnect()
	{
		return this.getChannel().disconnect();
	}

	@Override
	public ChannelFuture close()
	{
		return this.getChannel().close();
	}

	@Override
	public ChannelFuture deregister()
	{
		return this.getChannel().deregister();
	}

	@Override
	public ChannelFuture bind(final SocketAddress socketAddress, final ChannelPromise channelPromise)
	{
		return this.getChannel().bind(socketAddress, channelPromise);
	}

	@Override
	public ChannelFuture connect(final SocketAddress socketAddress, final ChannelPromise channelPromise)
	{
		return this.getChannel().connect(socketAddress, channelPromise);
	}

	@Override
	public ChannelFuture connect(final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise)
	{
		return this.getChannel().connect(socketAddress, socketAddress2, channelPromise);
	}

	@Override
	public ChannelFuture disconnect(final ChannelPromise channelPromise)
	{
		return this.getChannel().disconnect(channelPromise);
	}

	@Override
	public ChannelFuture close(final ChannelPromise channelPromise)
	{
		return this.getChannel().close(channelPromise);
	}

	@Override
	public ChannelFuture deregister(final ChannelPromise channelPromise)
	{
		return this.getChannel().deregister(channelPromise);
	}

	@Override
	public Channel read()
	{
		return this.getChannel().read();
	}

	@Override
	public ChannelFuture write(final Object o)
	{
		return this.getChannel().write(o);
	}

	@Override
	public ChannelFuture write(final Object o, final ChannelPromise channelPromise)
	{
		return this.getChannel().write(o, channelPromise);
	}

	@Override
	public Channel flush()
	{
		return this.getChannel().flush();
	}

	@Override
	public ChannelFuture writeAndFlush(final Object o, final ChannelPromise channelPromise)
	{
		return this.getChannel().writeAndFlush(o, channelPromise);
	}

	@Override
	public ChannelFuture writeAndFlush(final Object o)
	{
		return this.getChannel().writeAndFlush(o);
	}

	@Override
	public <T> Attribute<T> attr(final AttributeKey<T> tAttributeKey)
	{
		return this.getChannel().attr(tAttributeKey);
	}

	@Override
	public <T> boolean hasAttr(final AttributeKey<T> tAttributeKey)
	{
		return false;
	}

	@Override
	public int compareTo(final Channel o)
	{
		return this.getChannel().compareTo(o);
	}
}
