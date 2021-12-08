package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.GuildMemberLevel;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.AddGuildMemberComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.UpdateGuildMemberComposer;

@Event(id = Incoming.AcceptGuildMember)
public class AcceptGuildMemberEvent extends MessageEvent
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

		if (guild.getHabbo().getId() != connection.getHabbo().getId())
			return;

		if (guild.getMemberByHabboIgnoreRequest(habbo) == null)
			return;

		guild.getMemberByHabboIgnoreRequest(habbo).setLevel(GuildMemberLevel.MEMBER);

		connection.send(new UpdateGuildMemberComposer(guild.getMemberByHabbo(habbo)));
		connection.send(new AddGuildMemberComposer(habbo));

		if (habbo.isOnline() && habbo.isInRoom() && habbo.getCurrentRoom().getRoomData().getId() == guild.getRoom().getRoomData().getId())
		{
			connection.send(new AddGuildMemberComposer(habbo));
		}
	}

}
