package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboLandingAbstractTimerElementComposer extends MessageComposer
{

	public HabboLandingAbstractTimerElementComposer()
	{

	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboLandingAbstractTimerElement);
		response.appendBoolean(true); // ??
		response.appendInt(0); // ??
		response.appendInt(0); // ??
		response.appendInt(3); // user's score
		response.appendInt(0); // current level
		response.appendInt(7); // remaining score until next level
		response.appendInt(40); // current level compelted percentage
		response.appendString("friendshipChallenge"); // community meter name
		response.appendInt(0); // ??
		response.appendInt(0); // ??
		return response;
	}

}
