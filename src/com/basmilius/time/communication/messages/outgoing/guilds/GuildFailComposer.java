package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildFailComposer extends MessageComposer
{

	public static final int GUILD_FULL = 0;
	public static final int MAX_GUILDS = 1;
	public static final int GUILD_PRIVATE = 2;
	public static final int CANT_ACCEPT_MEMBERS = 3;
	public static final int HC_NEEDED = 4;

	private final int _reason;

	public GuildFailComposer(int _reason)
	{
		this._reason = _reason;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildFail);
		response.appendInt(this._reason);
		return response;
	}

}
