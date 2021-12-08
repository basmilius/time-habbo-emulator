package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class UserSearchResultsComposer extends MessageComposer
{

	private final List<Habbo> friends;
	private final List<Habbo> strangers;

	public UserSearchResultsComposer(List<Habbo> friends, List<Habbo> strangers)
	{
		this.friends = friends;
		this.strangers = strangers;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UserSearchResults);
		response.appendInt(this.friends.size());
		for (Habbo habbo : this.friends)
		{
			response.appendInt(habbo.getId());
			response.appendString(habbo.getUsername());
			response.appendString(habbo.getMotto());
			response.appendBoolean(habbo.isInRoom());
			response.appendBoolean(habbo.isOnline());
			response.appendString("");
			response.appendInt(1);
			response.appendString(habbo.getLook());
			response.appendString("");
		}
		response.appendInt(this.strangers.size());
		for (Habbo habbo : this.strangers)
		{
			response.appendInt(habbo.getId());
			response.appendString(habbo.getUsername());
			response.appendString(habbo.getMotto());
			response.appendBoolean(habbo.isInRoom());
			response.appendBoolean(habbo.isOnline());
			response.appendString("");
			response.appendInt(1);
			response.appendString(habbo.getLook());
			response.appendString("");
		}
		return response;
	}

}
