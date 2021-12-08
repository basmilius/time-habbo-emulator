package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.items.furniture.GuildFurniUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildBadgesComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomDataComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.SaveGuildBadge)
public class SaveGuildBadgeEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<Integer> badgeData = new ArrayList<>();

		final int guildId = packet.readInt();
		int partCount = (packet.readInt() - 3);
		final int guildBase = packet.readInt();
		final int guildBaseColor = packet.readInt();
		packet.readInt();

		while (partCount > 0)
		{
			badgeData.add(packet.readInt());
			partCount--;
		}

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		if (guild.getHabbo().getId() != connection.getHabbo().getId())
			return;

		guild.setBadgeData(Bootstrap.getEngine().getGame().getGuildManager().generateGuildImage(guildBase, guildBaseColor, badgeData));

		guild.getRoom().getRoomUnitsHandler().send(new RoomDataComposer(guild.getRoom(), true, false));

		final List<GuildFurniUserItem> items = guild.getRoom().getRoomObjectsHandler().getItems(GuildFurniUserItem.class);
		items.forEach(com.basmilius.time.habbohotel.items.UserItem::updateAllDataInRoom);

		guild.getRoom().getRoomUnitsHandler().send(new GuildBadgesComposer(guild.getRoom()));
	}

}
