package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomChatSettingsComposer extends MessageComposer
{

	private final Room room;

	public RoomChatSettingsComposer(Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomChatSettings);
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatMode());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatWeight());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatSpeed());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatHearingDistance());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getFloodControl());
		return response;
	}

}
