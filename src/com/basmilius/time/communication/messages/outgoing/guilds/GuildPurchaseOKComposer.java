package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildPurchaseOKComposer extends MessageComposer
{

	private final Guild guild;
	private final Room room;

	public GuildPurchaseOKComposer(final Guild guild, final Room room)
	{
		this.guild = guild;
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildPurchaseOK);
		response.appendInt(this.room.getRoomData().getId());
		response.appendInt(this.guild.getId());
		return response;
	}

}
