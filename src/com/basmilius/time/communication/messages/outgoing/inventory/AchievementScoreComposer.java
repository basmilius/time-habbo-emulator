package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class AchievementScoreComposer extends MessageComposer
{

	private final int score;

	public AchievementScoreComposer(int score)
	{
		this.score = score;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.AchievementScore);
		response.appendInt(this.score);
		return response;
	}

}
