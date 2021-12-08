package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.achievements.Achievement;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class AchievementsComposer extends MessageComposer
{

	private final Habbo habbo;

	public AchievementsComposer(Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		final List<Achievement> achievements = Bootstrap.getEngine().getGame().getAchievementsManager().getAchievements();

		response.init(Outgoing.Achievements);
		response.appendInt(achievements.size());
		for (final Achievement achievement : achievements)
		{
			achievement.serialize(response, this.habbo);
		}
		response.appendString("");
		return response;
	}

}
