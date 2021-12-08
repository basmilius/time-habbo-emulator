package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.GuildMemberLevel;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseOKComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildPurchaseOKComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomPanelComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomThicknessComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.RequestCreateGuild)
public class RequestCreateGuildEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String groupName = packet.readString();
		final String groupDescription = packet.readString();
		final Room groupHomeRoom = Bootstrap.getEngine().getGame().getRoomManager().getRoom(packet.readInt());
		final int groupColor1 = packet.readInt();
		final int groupColor2 = packet.readInt();
		final int badgeDataCount = (packet.readInt() - 3);
		final int guildBase = packet.readInt();
		final int guildBaseColor = packet.readInt();
		final List<Integer> badgeData = new ArrayList<>();
		packet.readInt();
		for (int i = 0; i < badgeDataCount; i++)
		{
			badgeData.add(packet.readInt());
		}

		if (groupHomeRoom == null)
			return;

		final Guild newGuild = Bootstrap.getEngine().getGame().getGuildManager().createGuild(groupName, groupDescription, connection.getHabbo(), groupHomeRoom, guildBase, guildBaseColor, Bootstrap.getEngine().getGame().getGuildManager().generateGuildImage(guildBase, guildBaseColor, badgeData), groupColor1, groupColor2);

		newGuild.addMember(connection.getHabbo(), GuildMemberLevel.ADMIN);

		connection.send(new PurchaseOKComposer(newGuild));
		connection.getHabbo().getSettings().updateCredits(-10);

		if (connection.getHabbo().isInRoom())
		{
			final Room room = connection.getHabbo().getCurrentRoom();

			for (final RoomUser roomUser : room.getRoomUnitsHandler().getUsers())
			{
				roomUser.getConnection().send(new RoomPanelComposer(room.getRoomData().getId(), room.getRoomData().getPermissions().isOwner(roomUser.getHabbo())));
			}

			room.getRoomUnitsHandler().send(new RoomThicknessComposer(room.getRoomData().getRoomDecoration().isWallHidden(), room.getRoomData().getRoomDecoration().getWallThickness(), room.getRoomData().getRoomDecoration().getFloorThickness()));
		}

		connection.send(new GuildPurchaseOKComposer(newGuild, groupHomeRoom));
	}

}
