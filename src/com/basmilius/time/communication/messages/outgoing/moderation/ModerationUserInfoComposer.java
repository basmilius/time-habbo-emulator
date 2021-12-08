package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.moderation.ModerationToolUserInfo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationUserInfoComposer extends MessageComposer
{

	private ModerationToolUserInfo moderationToolUserInfo;

	public ModerationUserInfoComposer(final ModerationToolUserInfo moderationToolUserInfo)
	{
		this.moderationToolUserInfo = moderationToolUserInfo;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationUserInfo);
		this.moderationToolUserInfo.serialize(response);
		return response;
	}

}
