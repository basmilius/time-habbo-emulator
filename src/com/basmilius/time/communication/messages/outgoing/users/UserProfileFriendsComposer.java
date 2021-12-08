package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.messenger.Friend;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.Collections;
import java.util.List;

public class UserProfileFriendsComposer extends MessageComposer
{

	private final Habbo habbo;

	public UserProfileFriendsComposer(Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		List<Friend> friendsILove = this.habbo.getMessenger().getFriendsWithRelation(1);
		List<Friend> friendsILike = this.habbo.getMessenger().getFriendsWithRelation(2);
		List<Friend> friendsIHate = this.habbo.getMessenger().getFriendsWithRelation(3);

		Collections.shuffle(friendsILove);
		Collections.shuffle(friendsILike);
		Collections.shuffle(friendsIHate);

		int counter = 0;
		if (friendsILove.size() > 0)
			counter += 1;
		if (friendsILike.size() > 0)
			counter += 1;
		if (friendsIHate.size() > 0)
			counter += 1;

		response.init(Outgoing.UserProfileFriends);
		response.appendInt(this.habbo.getId());
		response.appendInt(counter);

		if (friendsILove.size() > 0)
		{
			response.appendInt(1);
			response.appendInt(friendsILove.size());
			response.appendInt(friendsILove.get(0).getHabbo().getId());
			response.appendString(friendsILove.get(0).getHabbo().getUsername());
			response.appendString(friendsILove.get(0).getHabbo().getLook());
		}

		if (friendsILike.size() > 0)
		{
			response.appendInt(2);
			response.appendInt(friendsILike.size());
			response.appendInt(friendsILike.get(0).getHabbo().getId());
			response.appendString(friendsILike.get(0).getHabbo().getUsername());
			response.appendString(friendsILike.get(0).getHabbo().getLook());
		}

		if (friendsIHate.size() > 0)
		{
			response.appendInt(3);
			response.appendInt(friendsIHate.size());
			response.appendInt(friendsIHate.get(0).getHabbo().getId());
			response.appendString(friendsIHate.get(0).getHabbo().getUsername());
			response.appendString(friendsIHate.get(0).getHabbo().getLook());
		}

		return response;
	}

}
