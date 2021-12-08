package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumThreadUpdateComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.LocalizedNotificationComposer;

@Event(id = Incoming.GuildForumUpdateThread)
public class GuildForumUpdateThreadEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int threadId = packet.readInt();
		final boolean isPinned = packet.readBoolean();
		final boolean isLocked = packet.readBoolean();

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
		{
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
			return;
		}

		if (thread.isLocked() && !isLocked)
			connection.send(new LocalizedNotificationComposer.ForumThreadUnLockedComposer());
		else if (!thread.isLocked() && isLocked)
			connection.send(new LocalizedNotificationComposer.ForumThreadLockedComposer());

		if (thread.isPinned() && !isPinned)
			connection.send(new LocalizedNotificationComposer.ForumThreadUnPinnedComposer());
		else if (!thread.isLocked() && isPinned)
			connection.send(new LocalizedNotificationComposer.ForumThreadPinnedComposer());

		thread.setLocked(isLocked);
		thread.setPinned(isPinned);
		thread.save();

		connection.send(new GuildForumThreadUpdateComposer(thread));
	}

}
