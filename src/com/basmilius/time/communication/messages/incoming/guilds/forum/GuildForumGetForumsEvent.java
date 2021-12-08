package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumRootListComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Event(id = Incoming.GuildForumGetForums)
public class GuildForumGetForumsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int selectType = packet.readInt();
		final int startIndex = packet.readInt();
		int stopIndex = packet.readInt();

		if ((stopIndex - startIndex) > 20)
		{
			stopIndex = (startIndex + 20);
		}

		if (stopIndex > connection.getHabbo().getGuilds().size())
		{
			stopIndex = connection.getHabbo().getGuilds().size();
		}

		final List<Guild> guilds = new ArrayList<>();
		List<Guild> myGuilds = null;

		switch (selectType)
		{
			case 0:
				myGuilds = connection.getHabbo().getGuilds().subList(startIndex, stopIndex);
				break;
			case 1:
				myGuilds = Bootstrap.getEngine().getGame().getRoomManager().getPopularGuilds().subList(startIndex, stopIndex);
				break;
			case 2:
				myGuilds = Bootstrap.getEngine().getGame().getRoomManager().getPopularGuilds().subList(startIndex, stopIndex);
				break;
		}

		if (myGuilds == null)
			return;

		guilds.addAll(myGuilds.stream().filter(guild -> guild.isForumActive() && guild.getGuildForum() != null).collect(Collectors.toList()));

		for (final Guild guild : guilds)
			guild.getGuildForum().loadThreads();

		if (stopIndex > guilds.size())
		{
			stopIndex = guilds.size();
		}

		connection.send(new GuildForumRootListComposer(connection.getHabbo(), selectType, startIndex, stopIndex, guilds));
	}

}
