package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.*;

@Event(id = Incoming.LoadHeightmap)
public class LoadHeightmapEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final QueuedComposers composers = new QueuedComposers();
		final Room room = connection.getHabbo().getCurrentRoom();

		composers.appendComposer(new HeightmapComposer(room));
		composers.appendComposer(new RelativeHeightmapComposer(room));

		composers.appendComposer(new RoomUsersComposer(room));
		composers.appendComposer(new RoomUserStatusesComposer(room));

		if (connection.getHabbo().getRoomUser() == null)
			return;

		if (room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
		{
			connection.getHabbo().getRoomUser().getStatuses().put("flatctrl", "4 useradmin");
		}
		else if (room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
		{
			connection.getHabbo().getRoomUser().getStatuses().put("flatctrl", "1");
		}

		connection.getHabbo().getMessenger().sendUpdate();

		composers.appendComposer(new RoomFloorItemsComposer(room));
		composers.appendComposer(new RoomWallItemsComposer(room));
		composers.appendComposer(new RoomThicknessComposer(room.getRoomData().getRoomDecoration().isWallHidden(), room.getRoomData().getRoomDecoration().getWallThickness(), room.getRoomData().getRoomDecoration().getFloorThickness()));
		composers.appendComposer(new RoomChatSettingsComposer(room));
		composers.appendComposer(new RoomPanelComposer(room.getRoomData().getId(), room.getRoomData().getPermissions().isOwner(connection.getHabbo())));

		if (room.getRoomData().getRoomEvent() != null)
		{
			composers.appendComposer(new RoomEventComposer(room.getRoomData().getRoomEvent()));
		}

		for (final RoomUser u : room.getRoomUnitsHandler().getUsers())
		{
			if (u.getHabbo().getCurrentEffectId() != 0)
			{
				u.applyEffect(u.getHabbo().getCurrentEffectId());
			}
			if (u.getCarryItem() != 0)
			{
				u.carryItem(u.getCarryItem());
			}
		}

		composers.appendComposer(new RoomUpdateComposer(room.getRoomData().getId()));
		connection.send(composers);
	}

}
