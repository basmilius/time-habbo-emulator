package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.VoteRoom)
public class VoteRoomEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		// TODO Habbo that voted needs to be saved.
		room.getRoomData().setScore(room.getRoomData().getScore() + 1);
	}

}
