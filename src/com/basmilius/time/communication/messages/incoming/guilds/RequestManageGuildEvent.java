package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildEditFailComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.ManageGuildInfoComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.RequestManageGuild)
public class RequestManageGuildEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();

		if (!connection.getHabbo().getSubscriptions().hasSubscription("habbo_club"))
		{
			connection.send(new GuildEditFailComposer(GuildEditFailComposer.HC_NEEDED));
			return;
		}

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		final List<Room> goodRooms = new ArrayList<>();
		final List<Room> rooms = Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithOwner(connection.getHabbo());

		for (final Room room : rooms)
		{
			if (room.getRoomData().getGuild() == null)
				continue;

			goodRooms.add(room);
		}

		connection.send(new ManageGuildInfoComposer(guild, goodRooms));
	}

}
