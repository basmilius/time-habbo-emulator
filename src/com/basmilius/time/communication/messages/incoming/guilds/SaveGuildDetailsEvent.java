package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.items.furniture.GuildFurniUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildEditFailComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomDataComposer;

import java.util.List;

@Event(id = Incoming.SaveGuildDetails)
public class SaveGuildDetailsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final String guildName = packet.readString();
		final String guildDescription = packet.readString();

		if (Bootstrap.getEngine().getGame().getWordFilterManager().stringContainsBadWord(guildName + " ### " + guildDescription))
		{
			connection.send(new GuildEditFailComposer(GuildEditFailComposer.INVALID_GROUP_NAME));
			return;
		}

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		if (guild.getHabbo().getId() != connection.getHabbo().getId())
			return;

		guild.setName(guildName);
		guild.setDescription(guildDescription);

		guild.getRoom().getRoomUnitsHandler().send(new RoomDataComposer(guild.getRoom(), true, false));

		final List<GuildFurniUserItem> items = guild.getRoom().getRoomObjectsHandler().getItems(GuildFurniUserItem.class);
		items.forEach(com.basmilius.time.habbohotel.items.UserItem::updateAllDataInRoom);
	}

}
