package com.basmilius.time.communication.messages.incoming.rooms.competition;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.competition.RoomCompetition;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.competition.CompetitionEntrySubmitResultComposer;

@Event(id = Incoming.RequestRoomCompetition)
public class RequestRoomCompetitionEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		if (room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
		{
			final RoomCompetition competition = Bootstrap.getEngine().getGame().getRoomCompetitionManager().getRandomEnabledRoomCompetition();

			if (competition != null)
			{
				connection.send(new CompetitionEntrySubmitResultComposer(competition, 1));
			}
		}
	}

}
