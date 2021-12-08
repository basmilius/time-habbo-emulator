package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.messenger.Friend;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class FriendListUsersComposer extends MessageComposer
{

	private final List<Friend> friends;
	private final Habbo habbo;

	public FriendListUsersComposer(SocketConnection client)
	{
		this.habbo = client.getHabbo();
		this.friends = this.habbo.getMessenger().getFriends();
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendListUsers);
		response.appendInt(1);
		response.appendInt(0);
		response.appendInt(friends.size());

		for (final Friend friend : friends)
		{
			response.appendInt(friend.getHabbo().getId());
			response.appendString(friend.getHabbo().getUsername());
			response.appendInt((friend.getHabbo().getGender().equals("M")) ? 0 : 1);
			response.appendBoolean(friend.getHabbo().isOnline());
			response.appendBoolean(friend.getHabbo().isInRoom());
			response.appendString(friend.getHabbo().getLook());
			response.appendInt((friend.getHabbo().isOnline() && friend.getHabbo().getPermissions().contains("is_staff")) ? 1 : 0);
			response.appendString(friend.getHabbo().getMotto());
			response.appendString(""); // facebook name
			response.appendString(friend.getHabbo().getRealName());
			response.appendBoolean(Bootstrap.getEngine().getConfig().getBoolean("hotel.messenger.offline.messaging.enabled", false));
			response.appendBoolean(false); // no idea...
			response.appendBoolean(false); // Your friend is using pocket habbo
			response.appendShort(friend.getRelation());
		}
		return response;
	}

}
