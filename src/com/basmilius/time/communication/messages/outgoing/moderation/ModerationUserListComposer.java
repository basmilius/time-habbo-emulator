package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class ModerationUserListComposer extends MessageComposer
{

	private final List<Habbo> habbos;

	public ModerationUserListComposer(final List<Habbo> habbos)
	{
		this.habbos = habbos;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationUserList);
		response.appendInt(this.habbos.size());
		for (final Habbo habbo : habbos)
		{
			response.appendInt(habbo.getId());
			response.appendString(habbo.getUsername());
			response.appendString(habbo.isInRoom() ? habbo.getCurrentRoom().getRoomData().getRoomName() : "-");
		}
		return response;
	}

}
