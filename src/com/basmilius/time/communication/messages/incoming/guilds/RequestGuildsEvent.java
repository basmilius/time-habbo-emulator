package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.MyGuildsComposer;

@Event(id = Incoming.RequestGuilds)
public class RequestGuildsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new MyGuildsComposer(connection.getHabbo()));
	}

}
