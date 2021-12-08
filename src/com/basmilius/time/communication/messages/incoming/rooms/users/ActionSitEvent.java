package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ActionSit)
public class ActionSitEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		if (!connection.getHabbo().isInRoom())
			return;

		if (connection.getHabbo().getRoomUser() == null)
			return;

		if (connection.getHabbo().getRoomUser().getIsWalking())
			return;

		if (connection.getHabbo().getRoomUser().getNode().equals(connection.getHabbo().getRoomUser().getRoom().getRoomData().getRoomModel().getDoorNode()))
			return;

		if (!(connection.getHabbo().getRoomUser().getBodyRotation() % 2 == 0))
		{
			connection.getHabbo().getRoomUser().setRotation(connection.getHabbo().getRoomUser().getBodyRotation() - 1);
		}

		connection.getHabbo().getRoomUser().getStatuses().put("sit", "0.5");
		connection.getHabbo().getRoomUser().updateStatus();
	}

}
