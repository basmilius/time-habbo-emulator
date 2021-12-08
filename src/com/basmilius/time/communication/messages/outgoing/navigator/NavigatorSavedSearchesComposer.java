package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorSavedSearchesComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorSavedSearches);
		response.appendInt(0);

		response.appendInt(1);
		response.appendString("a");
		response.appendString("b");
		response.appendString("c");
		return response;
	}

}
