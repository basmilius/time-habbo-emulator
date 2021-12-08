package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.habbohotel.achievements.Achievement;
import com.basmilius.time.habbohotel.habbo.HabboAchievement;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class AchievementUnlockedComposer extends MessageComposer
{

	private final Achievement _achievement;
	private final HabboAchievement _habboAchievement;

	public AchievementUnlockedComposer(Achievement _achievement, HabboAchievement _habboAchievement)
	{
		this._achievement = _achievement;
		this._habboAchievement = _habboAchievement;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.AchievementUnlocked);
		response.appendInt(this._achievement.getId());
		response.appendInt(1);
		response.appendInt(144);
		response.appendString(this._achievement.getBadge(this._habboAchievement.getUserLevel()));
		response.appendInt(this._achievement.getCalculatedScore(this._habboAchievement.getUserLevel()));
		response.appendInt(this._achievement.getCalculatedDuckets(this._habboAchievement.getUserLevel()));
		response.appendInt(0);
		response.appendInt(10);
		response.appendInt(21);
		response.appendString("");
		response.appendString(this._achievement.getType());
		response.appendBoolean(true);
		return response;
	}

}
