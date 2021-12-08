package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.HabboRoomVisit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.Collections;
import java.util.List;

public class ModerationRoomVisitsComposer extends MessageComposer
{

	private Habbo habbo;
	private List<HabboRoomVisit> roomVisits;

	public ModerationRoomVisitsComposer(Habbo habbo, List<HabboRoomVisit> roomVisits)
	{
		this.habbo = habbo;
		this.roomVisits = roomVisits;

		Collections.sort(this.roomVisits, (o1, o2) -> (o1.getEnterTime() < o2.getEnterTime()) ? 1 : -1);
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationRoomVisits);
		response.appendInt(this.habbo.getId());
		response.appendString(this.habbo.getUsername());
		response.appendInt(this.roomVisits.size());
		for (HabboRoomVisit roomVisit : this.roomVisits)
		{
			response.appendInt(roomVisit.getRoom().getRoomData().getId());
			response.appendString(roomVisit.getRoom().getRoomData().getRoomName());
			response.appendInt(roomVisit.getHoursInRoom());
			response.appendInt(roomVisit.getMinutesInRoom());
		}
		return response;
	}

}
