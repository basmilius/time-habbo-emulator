package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorTagsComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.NavigatorTags);
		response.appendInt(3);

		response.appendString("Tag 1");
		response.appendInt(1);
		response.appendString("Tag 2");
		response.appendInt(2);
		response.appendString("Tag 3");
		response.appendInt(3);

		return response;
	}

}
