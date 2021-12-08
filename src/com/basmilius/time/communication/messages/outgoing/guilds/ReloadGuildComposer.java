package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ReloadGuildComposer extends MessageComposer
{

	private final Guild _guild;

	public ReloadGuildComposer(Guild _guild)
	{
		this._guild = _guild;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ReloadGuild);
		response.appendInt(this._guild.getId());
		return response;
	}

}
