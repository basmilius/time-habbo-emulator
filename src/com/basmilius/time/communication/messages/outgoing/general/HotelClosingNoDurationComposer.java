package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HotelClosingNoDurationComposer extends MessageComposer
{

	private final int minutesLeft;

	public HotelClosingNoDurationComposer(final int minutesLeft)
	{
		this.minutesLeft = minutesLeft;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HotelClosingNoDuration);
		response.appendInt(this.minutesLeft);
		return response;
	}

}
