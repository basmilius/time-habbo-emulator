package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

public class RoomSerializer implements ISerialize
{

	private final Room room;

	public RoomSerializer(final Room room)
	{
		this.room = room;
	}

	@Override
	public final void serialize(final ServerMessage response)
	{
		response.appendInt(this.room.getRoomData().getId());
		response.appendString(this.room.getRoomData().getRoomName());
		if (this.room.getRoomData().getPermissions().isPublicRoom())
		{
			response.appendInt(0);
			response.appendString("");
		}
		else
		{
			response.appendInt(this.room.getRoomData().getOwner().getId());
			response.appendString(this.room.getRoomData().getOwner().getUsername());
		}
		response.appendInt(this.room.getRoomData().getDoorMode().asInt());
		response.appendInt(this.room.getRoomUnitsHandler().getUsers().size());
		response.appendInt(this.room.getRoomData().getUsersLimit());
		response.appendString(this.room.getRoomData().getRoomDescription());
		response.appendInt(0);
		response.appendInt(this.room.getRoomData().getScore());
		response.appendInt(2); // can trade
		response.appendInt(this.room.getRoomData().getCategory());
		response.appendInt(this.room.getRoomData().getTags().length);
		for (final String tag : this.room.getRoomData().getTags())
		{
			response.appendString(tag);
		}
		
		int mode = 0;
		
		if (!this.room.getRoomData().getPermissions().isPublicRoom())
			mode += 8;
		
		if (this.room.getRoomData().getGuild() != null)
			mode += 2;
		
		if (this.room.getRoomData().getRoomEvent() != null)
			mode += 4;
		
		response.appendInt(mode);

		if (this.room.getRoomData().getGuild() != null)
		{
			response.appendInt(this.room.getRoomData().getGuild().getId());
			response.appendString(this.room.getRoomData().getGuild().getName());
			response.appendString(this.room.getRoomData().getGuild().getBadgeData());
		}

		if (this.room.getRoomData().getRoomEvent() != null)
		{
			response.appendString(this.room.getRoomData().getRoomEvent().getName());
			response.appendString(this.room.getRoomData().getRoomEvent().getDescription());
			response.appendInt(this.room.getRoomData().getRoomEvent().getTimeLeft());
		}
	}
	
}
