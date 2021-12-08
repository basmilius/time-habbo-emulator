package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumThreadUpdateComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.LocalizedNotificationComposer;

@Event(id = Incoming.GuildForumDeleteThreadToggle)
public class GuildForumDeleteThreadToggleEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int threadId = packet.readInt();
		final boolean hidden = (packet.readInt() == 10);

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
		{
			// ToDo: Notify the user.
			return;
		}

		if (!guild.isForumModerator(connection.getHabbo()))
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}

		final GuildForumThread thread = guild.getGuildForum().getThread(threadId);

		if (thread == null)
		{
			// ToDo: Notify the user.
			return;
		}

		if (thread.isHidden() && !hidden)
		{
			connection.send(new LocalizedNotificationComposer.ForumThreadRestoredComposer());
		}
		else
		{
			connection.send(new LocalizedNotificationComposer.ForumThreadHiddenComposer());
		}

		thread.setHabboHidder(connection.getHabbo());
		thread.setHidden(hidden);
		thread.save();

		connection.send(new GuildForumThreadUpdateComposer(thread));
	}

}
