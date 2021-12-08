package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.floorplaneditor.FloorPlanData;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.util.ObjectUtils;

@Event(id = Incoming.SaveRoomModel)
public class SaveRoomModelEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		String floorPlan;
		int door_x, door_y, door_rotation, wall_thickness, floor_thickness, wall_height;
		Room room = connection.getHabbo().getCurrentRoom();

		floorPlan = ObjectUtils.filterInjectionChars(packet.readString().replace("\r\n", "\n").replace("\r", "\n").replace(Character.toString((char) 13), "\n"));
		if (packet.readableBytes() > 4)
		{
			door_x = packet.readInt();
			door_y = packet.readInt();
			door_rotation = packet.readInt();
			wall_thickness = packet.readInt();
			floor_thickness = packet.readInt();
			if (packet.readableBytes() >= 4)
			{
				wall_height = packet.readInt();
			}
			else
			{
				wall_height = -1;
			}
		}
		else
		{
			door_x = room.getRoomData().getRoomModel().getDoorX();
			door_y = room.getRoomData().getRoomModel().getDoorY();
			door_rotation = room.getRoomData().getRoomModel().getDoorRotation();
			wall_thickness = room.getRoomData().getRoomDecoration().getWallThickness();
			floor_thickness = room.getRoomData().getRoomDecoration().getFloorThickness();
			wall_height = room.getRoomData().getRoomDecoration().getWallHeight();
		}

		room.getRoomData().setFloorPlanData(new FloorPlanData(room, floorPlan, new Node(door_x, door_y, (int) room.getRoomData().getRoomModel().getNodeHeights()[door_x][door_y]), door_rotation));

		room.getRoomData().getRoomDecoration().setFloorThickness(floor_thickness);
		room.getRoomData().getRoomDecoration().setWallThickness(wall_thickness);
		room.getRoomData().getRoomDecoration().setWallHeight(wall_height);

		room.unload(false);

		connection.getHabbo().goToRoom(room);
	}

}
