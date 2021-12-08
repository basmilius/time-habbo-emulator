package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

public class RoomDataSerializer implements ISerialize
{

	private final Room room;
	private final boolean data1;
	private final boolean data2;

	public RoomDataSerializer(final Room room, final boolean data1, final boolean data2)
	{
		this.room = room;
		this.data1 = data1;
		this.data2 = data2;
	}

	@Override
	public final void serialize(final ServerMessage response)
	{
		response.appendBoolean(this.data1);
		response.appendBody(new RoomSerializer(this.room));
		response.appendBoolean(this.data2);
		response.appendBoolean(false);
		response.appendBoolean(false);
		response.appendBoolean(false); // TODO: Check if the room is muted.
		response.appendInt(this.room.getRoomData().getPermissions().getMuteAccessLevel());
		response.appendInt(this.room.getRoomData().getPermissions().getKickAccessLevel());
		response.appendInt(this.room.getRoomData().getPermissions().getBanAccessLevel());
		response.appendBoolean(false);
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatMode());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatWeight());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatSpeed());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatHearingDistance());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getFloodControl());
	}
	
}
