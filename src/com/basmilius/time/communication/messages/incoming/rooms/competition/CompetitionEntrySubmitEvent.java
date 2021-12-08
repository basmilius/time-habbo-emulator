package com.basmilius.time.communication.messages.incoming.rooms.competition;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.competition.RoomCompetition;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.competition.CompetitionEntrySubmitResultComposer;

@Event(id = Incoming.CompetitionEntrySubmit)
public class CompetitionEntrySubmitEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String competitionName = packet.readString();
		int request = packet.readInt();

		RoomCompetition competition = Bootstrap.getEngine().getGame().getRoomCompetitionManager().getRoomCompetition(competitionName);

		if (competition == null)
			return;

		switch (request)
		{
			case 1:
			case 2:
				connection.send(new CompetitionEntrySubmitResultComposer(competition, request));
				break;
			case 3:
				/**
				 * TOTO
				 * Room owner accepts the competition.
				 */
				break;
			default:
				Bootstrap.getEngine().getLogging().logDebugLine("Got unknown competition request: " + request);
				break;
		}
	}

}
