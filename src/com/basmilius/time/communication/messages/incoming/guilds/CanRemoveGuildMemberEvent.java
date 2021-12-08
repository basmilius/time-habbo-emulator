package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.ConfirmRemoveGuildMemberComposer;

@Event(id = Incoming.CanRemoveGuildMember)
public class CanRemoveGuildMemberEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int habboId = packet.readInt();

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(habboId);

		if (guild == null || habbo == null)
			return;

		if (habboId != connection.getHabbo().getId() && guild.getHabbo().getId() != connection.getHabbo().getId())
			return;

		if (guild.getMemberByHabbo(habbo) == null)
			return;

		connection.send(new ConfirmRemoveGuildMemberComposer(guild, habbo));
	}

}
