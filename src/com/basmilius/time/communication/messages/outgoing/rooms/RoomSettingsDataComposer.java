package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomSettingsDataComposer extends MessageComposer
{

	private final Room room;

	public RoomSettingsDataComposer(Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomSettings);
		response.appendInt(this.room.getRoomData().getId());
		response.appendString(this.room.getRoomData().getRoomName());
		response.appendString(this.room.getRoomData().getRoomDescription());
		response.appendInt(this.room.getRoomData().getDoorMode().asInt());
		response.appendInt(this.room.getRoomData().getCategory());
		response.appendInt(this.room.getRoomData().getUsersLimit());
		response.appendInt(this.room.getRoomData().getUsersLimit());
		response.appendInt(this.room.getRoomData().getTags().length);
		for (final String tag : this.room.getRoomData().getTags())
		{
			response.appendString(tag);
		}
		response.appendInt(0); // TODO Users with rights
		response.appendInt(((this.room.getRoomData().getPermissions().isOtherPetsAllowed()) ? 1 : 0));
		response.appendInt(((this.room.getRoomData().getPermissions().isOtherPetsEatingAllowed()) ? 1 : 0));
		response.appendInt(((this.room.getRoomData().getPermissions().isWalkthroughAllowed()) ? 1 : 0));
		response.appendInt(((this.room.getRoomData().getRoomDecoration().isWallHidden()) ? 1 : 0));
		response.appendInt(this.room.getRoomData().getRoomDecoration().getWallThickness());
		response.appendInt(this.room.getRoomData().getRoomDecoration().getFloorThickness());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatMode());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatWeight());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatSpeed());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getChatHearingDistance());
		response.appendInt(this.room.getRoomData().getFreeFlowChat().getFloodControl());
		response.appendBoolean(false); // TODO Furni top list (?)
		response.appendInt(this.room.getRoomData().getPermissions().getMuteAccessLevel());
		response.appendInt(this.room.getRoomData().getPermissions().getKickAccessLevel());
		response.appendInt(this.room.getRoomData().getPermissions().getBanAccessLevel());
		return response;
	}

}
