package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.messenger.Friend;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendsUpdateComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.FriendRelationChange)
public class FriendChangeRelationEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int friendId = packet.readInt();
		int relationId = packet.readInt();

		Friend friend = connection.getHabbo().getMessenger().getFriend(friendId);
		List<Habbo> friendsToUpdate = new ArrayList<>();

		if (friend == null)
			return;

		friend.setRelation(relationId);
		friendsToUpdate.add(friend.getHabbo());

		connection.send(new FriendsUpdateComposer(connection.getHabbo(), friendsToUpdate, new ArrayList<>(), new ArrayList<>()));
	}

}
