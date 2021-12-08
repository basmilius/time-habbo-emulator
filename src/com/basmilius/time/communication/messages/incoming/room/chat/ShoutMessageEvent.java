package com.basmilius.time.communication.messages.incoming.room.chat;

import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.Shout)
public class ShoutMessageEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		final String text = packet.readString();
		int bubbleId = packet.readInt();

		if (bubbleId == 1)
			bubbleId = 0;

		if (!connection.getHabbo().isInRoom())
			return;

		if (connection.getHabbo().getRoomUser() == null)
			return;

		final Room room = connection.getHabbo().getCurrentRoom();
		final RoomUser unit = connection.getHabbo().getRoomUser();
		
		if (unit.getRoom().getRoomData().getId() != room.getRoomData().getId())
			return;
		
		unit.chatRoomMessage(ChatType.SHOUT, text, ChatBubble.fromInt(bubbleId));
	}

}
