package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.AddGuildMemberComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildInfoComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.ReloadGuildComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.RemoveGuildMemberComposer;

@Event(id = Incoming.RemoveGuildMember)
public class RemoveGuildMemberEvent extends MessageEvent
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

		guild.getMemberByHabbo(habbo).delete();

		connection.send(new RemoveGuildMemberComposer(guild, habbo));
		connection.send(new AddGuildMemberComposer(habbo));
		connection.send(new ReloadGuildComposer(guild));

		if (habbo.isOnline())
		{
			habbo.getConnection().send(new RemoveGuildMemberComposer(guild, habbo));
			habbo.getConnection().send(new AddGuildMemberComposer(habbo));
			habbo.getConnection().send(new ReloadGuildComposer(guild));
			habbo.getConnection().send(new GuildInfoComposer(habbo, guild, false));
		}
	}

}
