package com.basmilius.time.communication.messages.incoming.achievements;

import com.basmilius.time.habbohotel.achievements.TalentTrackCitizenship;
import com.basmilius.time.habbohotel.achievements.TalentTrackHelper;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.achievements.TalentTrackComposer;

@Event(id = Incoming.TalentTrack)
public class TalentTrackEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String talentTrack = packet.readString();

		if (talentTrack.equals("citizenship"))
		{
			connection.send(new TalentTrackComposer(new TalentTrackCitizenship(connection.getHabbo())));
		}
		else if (talentTrack.equals("helper"))
		{
			connection.send(new TalentTrackComposer(new TalentTrackHelper(connection.getHabbo())));
		}
	}

}
