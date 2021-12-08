package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildEditFailComposer extends MessageComposer
{

	public static final int ROOM_ALREADY_HOMEROOM = 0;
	public static final int INVALID_GROUP_NAME = 1;
	public static final int HC_NEEDED = 2;
	public static final int MAX_REACHED = 3;

	private final int _reason;

	public GuildEditFailComposer(int _reason)
	{
		this._reason = _reason;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildEditFail);
		response.appendInt(this._reason);
		return response;
	}

}
