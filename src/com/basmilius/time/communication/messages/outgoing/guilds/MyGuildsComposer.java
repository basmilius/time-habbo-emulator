package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class MyGuildsComposer extends MessageComposer
{

	private final Habbo habbo;
	private final List<Guild> guilds;

	public MyGuildsComposer(Habbo habbo)
	{
		this.habbo = habbo;
		this.guilds = Bootstrap.getEngine().getGame().getGuildManager().getGuildsFromHabbo(this.habbo);
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.MyGuilds);
		response.appendInt(this.guilds.size());
		for (Guild guild : this.guilds)
		{
			response.appendInt(guild.getId());
			response.appendString(guild.getName());
			response.appendString(guild.getBadgeData());
			response.appendString(guild.getColorOne().getColor());
			response.appendString(guild.getColorTwo().getColor());
			response.appendBoolean(this.habbo.getSettings().getFavoriteGuild() != null && this.habbo.getSettings().getFavoriteGuild().getId() == guild.getId());
			response.appendInt(0); // idk
			response.appendBoolean(false); // idk
		}
		return response;
	}

}
