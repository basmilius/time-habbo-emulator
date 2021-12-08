package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildItemInfoComposer;

@Event(id = Incoming.RequestGuildItemInfo)
public class RequestGuildItemInfoEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final int guildId = packet.readInt();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);
		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (item == null || guild == null)
			return;

		connection.send(new GuildItemInfoComposer(item, guild, connection.getHabbo()));
	}

}
