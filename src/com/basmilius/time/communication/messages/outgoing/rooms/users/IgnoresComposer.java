package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class IgnoresComposer extends MessageComposer
{

	private final List<Habbo> habbos;

	public IgnoresComposer(List<Habbo> habbos)
	{
		this.habbos = habbos;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Ignores);
		response.appendInt(this.habbos.size());
		for (Habbo habbo : habbos)
		{
			response.appendString(habbo.getUsername());
		}
		return response;
	}

}
