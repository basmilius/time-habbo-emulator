package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ConfirmRemoveGuildMemberComposer extends MessageComposer
{

	private final Guild guild;
	private final Habbo habbo;

	public ConfirmRemoveGuildMemberComposer(Guild guild, Habbo habbo)
	{
		this.guild = guild;
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ConfirmRemoveGuildMember);
		response.appendInt(this.habbo.getId());
		response.appendInt(this.guild.getRoom().getRoomObjectsHandler().getItemsWithOwner(this.habbo).size());
		return response;
	}

}
