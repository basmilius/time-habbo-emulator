package com.basmilius.time.network;

import com.basmilius.core.IDisposable;
import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.gameclients.GameClientManager;
import com.basmilius.time.communication.messages.HabboMessages;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class ServerManager extends IManager implements IDisposable
{

	private final ServerBootstrap bootstrap;
	private final GameClientManager clientManager;
	private ChannelFuture[] future;
	private HabboMessages messages;
	private boolean disposed;

	private String host;
	private int[] ports;

	public ServerManager()
	{
		this.bootstrap = new ServerBootstrap();
		this.bootstrap
				.group(new NioEventLoopGroup(10, Executors.newCachedThreadPool()), new NioEventLoopGroup(10, Executors.newCachedThreadPool()))
				.channel(NioServerSocketChannel.class)
				.childHandler(new ClientInitializer())
				.option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(65536, 65536, 65536))
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.SO_RCVBUF, 65536)
				.option(ChannelOption.SO_SNDBUF, 65536)
				.option(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(65536, 65536, 65536))
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childOption(ChannelOption.SO_RCVBUF, 65536)
				.childOption(ChannelOption.SO_SNDBUF, 65536)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.validate();

		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);

		this.clientManager = new GameClientManager();
		this.disposed = false;
	}

	@Override
	public void initialize()
	{
		try
		{
			this.messages = new HabboMessages();

			Bootstrap.getEngine().getLogging().logNoNewLine(ServerManager.class, "Starting game server .. ");

			this.future = new ChannelFuture[this.ports.length];
			int i = 0;
			for (final int port : this.ports)
			{
				this.future[i++] = this.bootstrap.bind(new InetSocketAddress(this.host, port));
			}

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (ChannelException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	public final void setHost(String host)
	{
		this.host = host;
	}

	public final void setPort(int[] ports)
	{
		this.ports = ports;
	}

	public final GameClientManager getClientManager()
	{
		return this.clientManager;
	}

	public final HabboMessages getHabboMessages ()
	{
		return this.messages;
	}

	@Override
	public final void dispose()
	{
		try
		{
			for (final ChannelFuture future : this.future)
			{
				future.channel().closeFuture().sync();
			}
			this.disposed = true;

			Bootstrap.getEngine().getLogging().log(ServerManager.class, "Server Manager disposed!");
		}
		catch (InterruptedException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	@Override
	public final boolean isDisposed ()
	{
		return this.disposed;
	}

}
