package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildBadgesComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomUserComposer;
import com.basmilius.time.communication.messages.outgoing.users.UserProfileComposer;

@Event(id = Incoming.RemoveFavoriteGuild)
public class RemoveFavoriteGuildEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.getHabbo().getSettings().setFavoriteGuild(null);
		connection.send(new UserProfileComposer(connection, connection.getHabbo()));

		if (connection.getHabbo().isInRoom())
		{
			final Room room = connection.getHabbo().getCurrentRoom();

			room.getRoomUnitsHandler().send(new RoomUserComposer(connection.getHabbo().getRoomUser()));
			room.getRoomUnitsHandler().send(new GuildBadgesComposer(room));
		}
	}
}
