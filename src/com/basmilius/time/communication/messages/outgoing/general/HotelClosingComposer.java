package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HotelClosingComposer extends MessageComposer
{

	private final int duration;
	private final int minutesLeft;

	public HotelClosingComposer(final int minutesLeft, final int duration)
	{
		this.duration = duration;
		this.minutesLeft = minutesLeft;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HotelClosing);
		response.appendBoolean(false); // ??
		response.appendInt(this.minutesLeft);
		response.appendInt(this.duration);
		return response;
	}

}
