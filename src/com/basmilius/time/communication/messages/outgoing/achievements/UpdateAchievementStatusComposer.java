package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.habbohotel.achievements.Achievement;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class UpdateAchievementStatusComposer extends MessageComposer
{

	private final Achievement _achievement;
	private final Habbo _habbo;

	public UpdateAchievementStatusComposer(Achievement _achievement, Habbo _habbo)
	{
		this._achievement = _achievement;
		this._habbo = _habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateAchievementStatus);
		this._achievement.serialize(response, this._habbo);
		return response;
	}

}
