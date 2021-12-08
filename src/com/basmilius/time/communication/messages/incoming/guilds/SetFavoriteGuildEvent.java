package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildBadgesComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomUserComposer;
import com.basmilius.time.communication.messages.outgoing.users.UserProfileComposer;

@Event(id = Incoming.SetFavoriteGuild)
public class SetFavoriteGuildEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null && guildId != 0)
			return;

		connection.getHabbo().getSettings().setFavoriteGuild(guild);
		connection.send(new UserProfileComposer(connection, connection.getHabbo()));

		if (connection.getHabbo().isInRoom())
		{
			final Room room = connection.getHabbo().getCurrentRoom();

			room.getRoomUnitsHandler().send(new RoomUserComposer(connection.getHabbo().getRoomUser()));
			room.getRoomUnitsHandler().send(new GuildBadgesComposer(room));
		}
	}
}
