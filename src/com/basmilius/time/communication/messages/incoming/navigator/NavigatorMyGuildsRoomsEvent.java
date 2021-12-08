package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFlatListComposer;

import java.util.stream.Collectors;

@Event(id = Incoming.NavigatorMyGuildsRooms)
public class NavigatorMyGuildsRoomsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new NavigatorFlatListComposer(connection.getHabbo().getGuilds().stream().map(Guild::getRoom).collect(Collectors.toList())));
	}

}
