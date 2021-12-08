package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.habbohotel.rooms.RoomEvent;
import com.basmilius.time.util.TimeUtils;

public class RoomEventComposer extends MessageComposer
{

	private final RoomEvent event;

	public RoomEventComposer(final RoomEvent event)
	{
		this.event = event;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomEvent);
		response.appendInt(123); // random?
		response.appendInt(this.event.getHabbo().getId());
		response.appendString(this.event.getHabbo().getUsername());
		response.appendInt(123); // random?
		response.appendInt(this.event.getRoom().getRoomData().getId());
		response.appendString(this.event.getName());
		response.appendString(this.event.getDescription());
		response.appendInt(0);
		response.appendInt(this.event.getStopTime() - TimeUtils.getUnixTimestamp());
		return response;
	}

}
