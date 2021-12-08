package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.habbohotel.habbo.messenger.FriendRequest;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class FriendRequestsComposer extends MessageComposer
{

	private final List<FriendRequest> requests;

	public FriendRequestsComposer(final List<FriendRequest> requests)
	{
		this.requests = requests;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendRequests);
		response.appendInt(requests.size());
		response.appendInt(requests.size());
		for (FriendRequest request : requests)
		{
			response.appendInt(request.getRequesterHabbo().getId());
			response.appendString(request.getRequesterHabbo().getUsername());
			response.appendString(request.getRequesterHabbo().getLook());
		}
		return response;
	}

}
