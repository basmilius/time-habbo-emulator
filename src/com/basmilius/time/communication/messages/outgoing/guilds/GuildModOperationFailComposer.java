package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildModOperationFailComposer extends MessageComposer
{

	public static final int HABBO_NO_LONGER_MEMBER = 0;
	public static final int HABBO_ALREADY_REJECTED = 1;

	private final int _userId;
	private final int _reason;

	public GuildModOperationFailComposer(int _userId, int _reason)
	{
		this._userId = _userId;
		this._reason = _reason;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildModOperationFail);
		response.appendInt(this._userId);
		response.appendInt(this._reason);
		return response;
	}

}
