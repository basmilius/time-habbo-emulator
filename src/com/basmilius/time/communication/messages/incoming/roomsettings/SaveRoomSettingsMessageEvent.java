package com.basmilius.time.communication.messages.incoming.roomsettings;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.DoorMode;
import com.basmilius.time.habbohotel.enums.TradingMode;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomChatSettingsComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomSettingsSavedComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomThicknessComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomUpdateComposer;

@Event(id = Incoming.SaveRoomSettings)
public class SaveRoomSettingsMessageEvent extends MessageEvent
{

	@SuppressWarnings("unused")
	@Override
	public void handle() throws Exception
	{
		final int roomId = packet.readInt();
		final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);
		
		if (room == null || !room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
			return;

		final String roomName = packet.readString();
		final String roomDescription = packet.readString();
		final DoorMode doorMode = DoorMode.fromInt(packet.readInt());
		final String doorModePassword = packet.readString();
		final int maxUsers = packet.readInt();
		final int category = packet.readInt();
		
		final int tagCount = packet.readInt();
		final String[] tags = new String[tagCount];
		int i = 0;
		while (i < tagCount)
		{
			tags[i] = packet.readString();
			i++;
		}

		final TradingMode tradingMode = TradingMode.fromInt(packet.readInt());
		final boolean allowOtherPets = packet.readBoolean();
		final boolean allowOtherPetsEat = packet.readBoolean();
		final boolean allowWalkThrough = packet.readBoolean();
		final boolean hideWall = packet.readBoolean();
		final int wallThickness = packet.readInt();
		final int floorThickness = packet.readInt();
		final int whoCanMute = packet.readInt();
		final int whoCanKick = packet.readInt();
		final int whoCanBan = packet.readInt();
		final int chatMode = packet.readInt();
		final int chatWeight = packet.readInt();
		final int chatSpeed = packet.readInt();
		final int chatHearingDistance = packet.readInt();
		final int antiFloodControl = packet.readInt();
		final boolean allowFurniToplist = packet.readBoolean();

		room.getRoomData().setCategory(category);
		room.getRoomData().setDoorMode(doorMode);
		room.getRoomData().setRoomDescription(roomDescription);
		room.getRoomData().setRoomName(roomName);
		room.getRoomData().setRoomPassword(doorModePassword);
		room.getRoomData().setUsersLimit(maxUsers);
		room.getRoomData().setTags(tags);
		room.getRoomData().setTradingMode(tradingMode);
		
		room.getRoomData().getFreeFlowChat().setChatHearingDistance(chatHearingDistance);
		room.getRoomData().getFreeFlowChat().setChatMode(chatMode);
		room.getRoomData().getFreeFlowChat().setChatSpeed(chatSpeed);
		room.getRoomData().getFreeFlowChat().setChatWeight(chatWeight);
		room.getRoomData().getFreeFlowChat().setFloodControl(antiFloodControl);

		room.getRoomData().getPermissions().setIsOtherPetsAllowed(allowOtherPets);
		room.getRoomData().getPermissions().setIsOtherPetsEatingAllowed(allowOtherPetsEat);
		room.getRoomData().getPermissions().setIsWalkthroughAllowed(allowWalkThrough);
		room.getRoomData().getPermissions().setBanAccessLevel(whoCanBan);
		room.getRoomData().getPermissions().setKickAccessLevel(whoCanKick);
		room.getRoomData().getPermissions().setMuteAccessLevel(whoCanMute);

		room.getRoomData().getRoomDecoration().setIsWallHidden(hideWall);
		room.getRoomData().getRoomDecoration().setWallThickness(wallThickness);
		room.getRoomData().getRoomDecoration().setFloorThickness(floorThickness);

		room.getRoomUnitsHandler().send(new RoomUpdateComposer(room.getRoomData().getId()));
		room.getRoomUnitsHandler().send(new RoomThicknessComposer(room.getRoomData().getRoomDecoration().isWallHidden(), room.getRoomData().getRoomDecoration().getWallThickness(), room.getRoomData().getRoomDecoration().getFloorThickness()));
		room.getRoomUnitsHandler().send(new RoomChatSettingsComposer(room));
		connection.send(new RoomSettingsSavedComposer(room.getRoomData().getId()));
	}

}
