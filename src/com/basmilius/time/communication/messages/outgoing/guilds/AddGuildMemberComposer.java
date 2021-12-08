package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class AddGuildMemberComposer extends MessageComposer
{

	private final Habbo _habbo;

	public AddGuildMemberComposer(Habbo _habbo)
	{
		this._habbo = _habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.AddGuildMember);
		response.appendInt(this._habbo.getId());
		return response;
	}

}
