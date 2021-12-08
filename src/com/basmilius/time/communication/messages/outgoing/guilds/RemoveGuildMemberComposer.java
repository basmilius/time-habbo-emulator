package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RemoveGuildMemberComposer extends MessageComposer
{

	private final Guild _guild;
	private final Habbo _habbo;

	public RemoveGuildMemberComposer(Guild _guild, Habbo _habbo)
	{
		this._guild = _guild;
		this._habbo = _habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RemoveGuildMember);
		response.appendInt(this._guild.getId());
		response.appendInt(this._habbo.getId());
		return response;
	}

}
