package com.basmilius.time.communication.messages.incoming.room.avatar;

import com.basmilius.time.habbohotel.enums.AvatarAction;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.AvatarExpression)
public class AvatarExpressionMessageEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		final int actionId = packet.readInt();

		if (!connection.getHabbo().isInRoom())
			return;

		final RoomUser roomUser = connection.getHabbo().getRoomUser();
		
		if (roomUser == null)
			return;

		if (actionId == 5)
		{
			roomUser.setIdle(true);
			return;
		}

		roomUser.applyAction(AvatarAction.fromInt(actionId));
	}

}
