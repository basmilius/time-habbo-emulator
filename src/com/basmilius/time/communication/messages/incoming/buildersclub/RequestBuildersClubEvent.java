package com.basmilius.time.communication.messages.incoming.buildersclub;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.buildersclub.BuildersClubFurniCountComposer;
import com.basmilius.time.communication.messages.outgoing.buildersclub.BuildersClubStatusComposer;

@Event(id = Incoming.RequestBuildersClub)
public class RequestBuildersClubEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new BuildersClubStatusComposer(600 * 600, 100));
		connection.send(new BuildersClubFurniCountComposer(0));
	}

}
