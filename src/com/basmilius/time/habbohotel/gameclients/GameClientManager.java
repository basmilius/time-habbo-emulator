package com.basmilius.time.habbohotel.gameclients;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameClientManager
{

	private final ConcurrentMap<Integer, SocketConnection> connections;
	private int counter = 0;

	public GameClientManager ()
	{
		this.connections = new ConcurrentHashMap<>();
	}

	public ConcurrentMap<Integer, SocketConnection> getSessions ()
	{
		return this.connections;
	}

	public boolean containsClient (final Channel channel)
	{
		return this.getConnection(channel) != null;
	}

	public SocketConnection getConnection (final int channel)
	{
		if (this.connections.containsKey(channel))
		{
			return this.connections.get(channel);
		}
		else
		{
			return null;
		}
	}

	public SocketConnection getConnection (final Channel channel)
	{
		for (final SocketConnection connection : this.connections.values())
			if (connection.getSocket().getChannel().equals(channel))
				return connection;

		return null;
	}

	public boolean addClient (final ChannelHandlerContext context) throws Exception
	{
		GameClientChannel gameClientChannel = new GameClientChannel(++this.counter, context);

		return this.connections.putIfAbsent(this.counter, gameClientChannel.getConnection()) == null;
	}

	public void disposeConnection (final int channel)
	{
		final SocketConnection client = this.getConnection(channel);
		if (client != null)
		{
			client.dispose();
			this.connections.remove(channel);
		}
	}

	public void disposeClient (final Channel channel)
	{
		if (this.containsClient(channel))
		{
			GameClientChannel clientChannel = this.getConnection(channel).getSocket();
			this.getConnection(channel).dispose();
			this.connections.remove(clientChannel.getId());
		}
	}

	public void sendBroadcastResponse (final MessageComposer composer)
	{
		for (SocketConnection client : this.connections.values())
		{
			if (!client.hasHabbo())
				continue;
			client.send(composer);
		}
	}

	public void sendBroadcastResponse (final MessageComposer composer, final String minPermission)
	{
		for (SocketConnection client : this.connections.values())
		{
			if (!client.hasHabbo() || (client.hasHabbo() && !client.getHabbo().getPermissions().contains(minPermission)))
				continue;
			client.send(composer);
		}
	}

}