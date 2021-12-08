package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.AddGuildMemberComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildEditFailComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildInfoComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.MyGuildsComposer;

@Event(id = Incoming.RequestMembership)
public class RequestMembershipEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();

		if (connection.getHabbo().getGuilds().size() > (connection.getHabbo().getSubscriptions().hasSubscription("habbo_club") ? 100 : 50))
		{
			connection.send(new GuildEditFailComposer(GuildEditFailComposer.MAX_REACHED));
			return;
		}

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		if (guild.getType() == 2) // Guild is locked.
			return;

		if (guild.getHasMember(connection.getHabbo()))
			return;

		guild.addMember(connection.getHabbo());

		connection.send(new MyGuildsComposer(connection.getHabbo()));
		connection.send(new GuildInfoComposer(connection.getHabbo(), guild, false));
		connection.send(new AddGuildMemberComposer(connection.getHabbo()));
	}

}
