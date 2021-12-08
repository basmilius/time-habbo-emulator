package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FriendListInitComposer extends MessageComposer
{

	private final Habbo habbo;

	public FriendListInitComposer(SocketConnection client)
	{
		this.habbo = client.getHabbo();
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendListInit);
		response.appendInt(300);
		response.appendInt(300);
		response.appendInt(3);
		response.appendInt(1100);
		if (Bootstrap.getEngine().getConfig().getString("hotel.messenger.friends.category.staff", "").isEmpty())
		{
			response.appendInt(0);
		}
		else
		{
			response.appendInt(1);
			response.appendInt(1);
			response.appendString(Bootstrap.getEngine().getConfig().getString("hotel.messenger.friends.category.staff", ""));
		}
		return response;
	}

}
