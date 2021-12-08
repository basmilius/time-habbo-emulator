package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildItemInfoComposer extends MessageComposer
{

	private final UserItem item;
	private final Guild guild;
	private final Habbo habbo;

	public GuildItemInfoComposer(UserItem item, Guild guild, Habbo habbo)
	{
		this.item = item;
		this.guild = guild;
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildItemInfo);
		response.appendInt(this.item.getId());
		response.appendInt(this.guild.getId());
		response.appendString(this.guild.getName());
		response.appendInt(this.guild.getRoom().getRoomData().getId());
		response.appendBoolean(this.guild.getHasMember(this.habbo));
		response.appendBoolean(false);
		return response;
	}

}
