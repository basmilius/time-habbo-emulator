package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.ArrayList;
import java.util.List;

public class GuildBadgesComposer extends MessageComposer
{

	private final List<Guild> guilds;

	public GuildBadgesComposer(final Room room)
	{
		this.guilds = new ArrayList<>();

		for (final RoomUser user : room.getRoomUnitsHandler().getUsers())
		{
			final Guild guild = user.getHabbo().getSettings().getFavoriteGuild();

			if (guild == null)
				continue;

			if (this.guilds.contains(guild))
				continue;

			this.guilds.add(guild);
		}
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildBadges);
		response.appendInt(this.guilds.size());
		for (final Guild guild : this.guilds)
		{
			response.appendInt(guild.getId());
			response.appendString(guild.getBadgeData());
		}
		return response;
	}

}
