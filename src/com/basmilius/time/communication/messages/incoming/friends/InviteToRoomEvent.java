package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.InviteMessageComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.InviteToRoom)
public class InviteToRoomEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<Habbo> habbos = new ArrayList<>();
		final int max = packet.readInt();
		for (int i = 0; i < max; i++)
		{
			final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(packet.readInt());
			if (habbo == null)
				continue;
			if (!habbo.isOnline())
				continue;
			habbos.add(habbo);
		}

		final String message = packet.readString();

		for (Habbo habbo : habbos)
		{
			habbo.getConnection().send(new InviteMessageComposer(connection.getHabbo().getId(), message));
		}
	}

}
