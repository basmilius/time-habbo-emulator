package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.moderation.ModerationToolRoomInfo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationRoomInfoComposer extends MessageComposer
{

	private ModerationToolRoomInfo moderationToolRoomInfo;

	public ModerationRoomInfoComposer(final ModerationToolRoomInfo moderationToolRoomInfo)
	{
		this.moderationToolRoomInfo = moderationToolRoomInfo;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationRoomInfo);
		this.moderationToolRoomInfo.serialize(response);
		return response;
	}

}
