package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomScoreComposer extends MessageComposer
{

	private final int score;
	private final boolean hasVoted;

	public RoomScoreComposer(Integer score, Boolean hasVoted)
	{
		this.score = score;
		this.hasVoted = hasVoted;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomScore);
		response.appendInt(this.score);
		response.appendBoolean(this.hasVoted);
		return response;
	}

}
