package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.RoomModel;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FloorPlanEditorDoorComposer extends MessageComposer
{

	private final RoomModel model;

	public FloorPlanEditorDoorComposer(RoomModel model)
	{
		this.model = model;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.FloorPlanEditorDoor);
		response.appendInt(this.model.getDoorX());
		response.appendInt(this.model.getDoorY());
		response.appendInt(this.model.getDoorRotation());
		return response;
	}

}
