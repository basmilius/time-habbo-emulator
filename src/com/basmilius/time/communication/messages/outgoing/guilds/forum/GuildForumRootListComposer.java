package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class GuildForumRootListComposer extends MessageComposer
{

	private final Habbo habbo;
	private final int selectType;
	private final int startIndex;
	private final int stopIndex;
	private final List<Guild> guilds;

	public GuildForumRootListComposer(final Habbo habbo, final int selectType, final int startIndex, final int stopIndex, final List<Guild> guilds)
	{
		this.habbo = habbo;
		this.selectType = selectType;
		this.startIndex = startIndex;
		this.stopIndex = stopIndex;
		this.guilds = guilds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumRootList);
		response.appendInt(this.selectType);
		response.appendInt(this.guilds.size());
		response.appendInt(this.startIndex);
		response.appendInt(this.stopIndex);
		for (final Guild guild : this.guilds)
		{
			guild.getGuildForum().serializeData(this.habbo, response);
		}
		return response;
	}

}
