package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationReplyComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;

@Event(id = Incoming.ModerationSendRoomMessage)
public class ModerationSendRoomMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		final int sendType = packet.readInt();
		final String message = packet.readString();
		final String reason = packet.readString();

		final Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		/**
		 * TODO Log moderation action for security purposes.
		 */

		switch (sendType)
		{
			case 0:
				room.getRoomUnitsHandler().send(new GenericAlertComposer(message));
				break;
			case 3:
				room.getRoomUnitsHandler().send(new ModerationReplyComposer(message));
				break;
			default:
				break;
		}
	}

}
