package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.UserSearchResultsComposer;

import java.util.List;
import java.util.Map;

@Event(id = Incoming.SearchUsers)
public class SearchUsersEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String query = packet.readString();

		if (query.replace(" ", "").isEmpty())
			return; // Empty query

		Map<String, List<Habbo>> results = connection.getHabbo().getMessenger().getSearchByQuery(query);

		connection.send(new UserSearchResultsComposer(results.get("friends"), results.get("strangers")));
	}

}
