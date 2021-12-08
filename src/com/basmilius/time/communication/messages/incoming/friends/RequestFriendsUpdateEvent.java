package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.messenger.Friend;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendsUpdateComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Event(id = Incoming.RequestFriendsUpdate)
public class RequestFriendsUpdateEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		List<Habbo> habbosToUpdate = new ArrayList<>();
		List<Friend> friendsToUpdate = connection.getHabbo().getMessenger().getFriendsToUpdate();

		habbosToUpdate.addAll(friendsToUpdate.stream().map(Friend::getHabbo).collect(Collectors.toList()));

		connection.send(new FriendsUpdateComposer(connection.getHabbo(), habbosToUpdate, new ArrayList<>(), new ArrayList<>()));

		connection.getHabbo().getMessenger().getFriendsToUpdate().clear();
	}

}
