package com.basmilius.time.communication.messages.outgoing.rooms.competition;

import com.basmilius.time.habbohotel.rooms.competition.RoomCompetition;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CompetitionEntrySubmitResultComposer extends MessageComposer
{

	public static final int SUBMITTED = 0;
	public static final int CAN_SUBMIT = 1;
	public static final int SUBMIT_VERIFY = 2;
	public static final int NEED_ITEMS = 3;
	public static final int ROOM_LOCKED = 4;
	public static final int ROOM_TOO_OLD = 5;
	public static final int READ_TOS = 6;

	private RoomCompetition roomCompetition;
	private int result;

	public CompetitionEntrySubmitResultComposer(RoomCompetition roomCompetition, int result)
	{
		this.roomCompetition = roomCompetition;
		this.result = result;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.CompetitionEntrySubmitResult);
		response.appendInt(this.roomCompetition.getId());
		response.appendString(this.roomCompetition.getName());
		response.appendInt(this.result);
		response.appendInt(this.roomCompetition.getRequiredItems().length);
		for (String requiredItem : this.roomCompetition.getRequiredItems())
		{
			response.appendString(requiredItem);
		}
		response.appendInt(0);
		return response;
	}

}
