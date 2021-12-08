package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ApplyDanceComposer extends MessageComposer
{

	private final RoomUnit unit;
	private final int danceId;

	public ApplyDanceComposer(RoomUnit unit, Integer danceId)
	{
		this.unit = unit;
		this.danceId = danceId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ApplyDance);
		response.appendInt(this.unit.getUnitId());
		response.appendInt(this.danceId);
		return response;
	}

}
