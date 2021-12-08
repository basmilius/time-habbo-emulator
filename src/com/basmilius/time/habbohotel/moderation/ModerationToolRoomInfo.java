package com.basmilius.time.habbohotel.moderation;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;

public class ModerationToolRoomInfo implements ISerialize
{

	private final Room room;

	public ModerationToolRoomInfo(final Room room)
	{
		this.room = room;
	}

	@Override
	public void serialize(ServerMessage response)
	{
		response.init(Outgoing.ModerationRoomInfo);
		response.appendInt(this.room.getRoomData().getId());
		response.appendInt(this.room.getRoomUnitsHandler().getUsers().size());
		response.appendBoolean(this.room.getRoomData().getOwner().isInRoom() && this.room.getRoomData().getOwner().getCurrentRoom() == this.room);
		response.appendInt(this.room.getRoomData().getOwner().getId());
		response.appendString(this.room.getRoomData().getOwner().getUsername());
		response.appendBoolean(this.room.getRoomData().getRoomEvent() != null);
		response.appendString(this.room.getRoomData().getRoomName());
		response.appendString(this.room.getRoomData().getRoomDescription());
		response.appendInt(this.room.getRoomData().getTags().length);
		for (final String tag : this.room.getRoomData().getTags())
		{
			response.appendString(tag);
		}
	}

}
