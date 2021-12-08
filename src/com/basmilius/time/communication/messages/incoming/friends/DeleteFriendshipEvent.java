package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendsUpdateComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.DeleteFriendship)
public class DeleteFriendshipEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int count = packet.readInt();
		List<Habbo> habbosToRemove = new ArrayList<>();

		for (int i = 0; i < count; i++)
		{
			final int habboId = packet.readInt();
			final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabbo(habboId);

			if (habbo == null)
				continue;

			boolean success = connection.getHabbo().getMessenger().deleteFriendship(habbo);

			if (success)
				habbosToRemove.add(habbo);
		}

		connection.send(new FriendsUpdateComposer(connection.getHabbo(), habbosToRemove, new ArrayList<>(), habbosToRemove));
	}

}
