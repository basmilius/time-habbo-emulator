package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorCategoriesComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorMetaDataComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorSavedSearchesComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorSearchResultSetComposer;

@Event(id = Incoming.Navigator)
public class NavigatorEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new NavigatorMetaDataComposer());
		//client.send(new NavigatorLiftedRoomsComposer()); ?? removed from swf :C
		connection.send(new NavigatorSavedSearchesComposer());
		connection.send(new NavigatorCategoriesComposer());
		connection.send(new NavigatorSearchResultSetComposer("official", "", Bootstrap.getEngine().getGame().getRoomManager().getPopularRooms()));
	}

}
