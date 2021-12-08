package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HotelClosedComposer extends MessageComposer
{

	private final int _openHours;
	private final int _openMinutes;

	public HotelClosedComposer(int _openHours, int _openMinutes)
	{
		this._openHours = _openHours;
		this._openMinutes = _openMinutes;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HotelClosed);
		response.appendInt(this._openHours);
		response.appendInt(this._openMinutes);
		return response;
	}

}
