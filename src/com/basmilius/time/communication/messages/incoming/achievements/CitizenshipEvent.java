package com.basmilius.time.communication.messages.incoming.achievements;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.achievements.CitizenshipComposer;

@Event(id = Incoming.Citizenship)
public class CitizenshipEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new CitizenshipComposer(packet.readString(), connection.getHabbo()));
	}

}
