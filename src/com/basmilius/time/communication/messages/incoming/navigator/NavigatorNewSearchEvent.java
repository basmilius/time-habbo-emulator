package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorSearchResultSetComposer;

@Event(id = Incoming.NavigatorNewSearch)
public class NavigatorNewSearchEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String searchCode = packet.readString();
		String text = packet.readString();

		if (searchCode.equalsIgnoreCase("official_view"))
			searchCode = "official";

		connection.send(new NavigatorSearchResultSetComposer(searchCode, text, Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithQuery(text)));
	}

}
