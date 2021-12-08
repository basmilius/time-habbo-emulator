package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFavoriteChangeComposer;

@Event(id = Incoming.NavigatorAddFavorite)
public class NavigatorAddFavoriteEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int roomId = packet.readInt();

		if (!connection.getHabbo().getSettings().getRoomFavorites().isFavorited(roomId))
		{
			connection.getHabbo().getSettings().getRoomFavorites().addRoom(roomId);

			connection.send(new NavigatorFavoriteChangeComposer(roomId, true));
		}
	}

}
