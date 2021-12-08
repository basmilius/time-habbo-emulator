package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFlatListComposer;

@Event(id = Incoming.NavigatorRoomsWithHighestScores)
public class NavigatorRoomsWithHighestScoresEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new NavigatorFlatListComposer(Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithHighestScores()));
	}

}
