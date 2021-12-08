package com.basmilius.time.communication.messages.incoming.achievements;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.achievements.AchievementsLevelsComposer;

@Event(id = Incoming.RequestAchievementsLevels)
public class RequestAchievementsLevelsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new AchievementsLevelsComposer(Bootstrap.getEngine().getGame().getAchievementsManager().getAchievements()));
	}

}
