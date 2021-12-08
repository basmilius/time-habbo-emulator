package com.basmilius.time.communication.messages.incoming.achievements;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.achievements.AchievementsComposer;

@Event(id = Incoming.RequestAchievements)
public class RequestAchievementsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new AchievementsComposer(connection.getHabbo()));
	}

}
