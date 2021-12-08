package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendFollowErrorComposer;
import com.basmilius.time.communication.messages.outgoing.general.RoomForwardComposer;

@Event(id = Incoming.FollowFriend)
public class FollowFriendEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int friendId = packet.readInt();
		final Habbo friend = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(friendId);

		if (!connection.getHabbo().getMessenger().isFriendWith(friend) && !connection.getHabbo().getPermissions().contains("acc_supporttool"))
		{
			connection.send(new FriendFollowErrorComposer(FriendFollowErrorComposer.NOT_IN_FRIENDLIST));
			return;
		}
		else if (!friend.isOnline())
		{
			connection.send(new FriendFollowErrorComposer(FriendFollowErrorComposer.FRIEND_OFLINE));
			return;
		}
		else if (!friend.isInRoom())
		{
			connection.send(new FriendFollowErrorComposer(FriendFollowErrorComposer.FRIEND_IN_HOTELVIEW));
			return;
		}
		else if (friend.getSettings().getPreventedFollowing())
		{
			connection.send(new FriendFollowErrorComposer(FriendFollowErrorComposer.CANT_FOLLOW));
			return;
		}
		else if (connection.getHabbo().isInRoom() && friend.getCurrentRoom().getRoomData().getId() == connection.getHabbo().getCurrentRoom().getRoomData().getId())
		{
			return;
		}

		connection.send(new RoomForwardComposer(friend.getCurrentRoom().getRoomData().getId()));
	}

}
