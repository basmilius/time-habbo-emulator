package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class UpdateLookInRoomComposer extends MessageComposer
{

	private final int unitId;
	private final String figure;
	private final String gender;
	private final String motto;
	private final int achievementScore;

	public UpdateLookInRoomComposer(int unitId, String figure, String gender, String motto, int achievementScore)
	{
		this.unitId = unitId;
		this.figure = figure;
		this.gender = gender;
		this.motto = motto;
		this.achievementScore = achievementScore;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.UpdateLookInRoom);
		response.appendInt(this.unitId);
		response.appendString(this.figure);
		response.appendString(this.gender);
		response.appendString(this.motto);
		response.appendInt(this.achievementScore);
		return response;
	}

}
