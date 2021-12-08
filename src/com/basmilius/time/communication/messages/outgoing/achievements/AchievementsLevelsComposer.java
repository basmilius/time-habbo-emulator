package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.habbohotel.achievements.Achievement;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class AchievementsLevelsComposer extends MessageComposer
{

	private final List<Achievement> _achievements;

	public AchievementsLevelsComposer(List<Achievement> _achievements)
	{
		this._achievements = _achievements;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.AchievementsLevels);
		response.appendInt(this._achievements.size());
		for (Achievement _achievement : this._achievements)
		{
			response.appendString(_achievement.getAchievementName());
			response.appendInt(_achievement.getTotalLevels());
			for (int i = 1; i <= _achievement.getTotalLevels(); i++)
			{
				response.appendInt(i);
				response.appendInt(_achievement.getCalculatedProgress(i));
			}
		}
		return response;
	}

}
