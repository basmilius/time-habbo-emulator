package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumMessage;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumMessageUpdateComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.LocalizedNotificationComposer;

@Event(id = Incoming.GuildForumDeleteMessageToggle)
public class GuildForumDeleteMessageToggleEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int threadId = packet.readInt();
		final int messageId = packet.readInt();
		final boolean hidden = (packet.readInt() == 10);

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

		final GuildForumMessage message = thread.getMessage(messageId);

		if (message == null)
		{
			return;
		}

		if (message.isHidden() && !hidden)
		{
			connection.send(new LocalizedNotificationComposer.ForumMessageRestoredComposer());
		}
		else
		{
			connection.send(new LocalizedNotificationComposer.ForumMessageHiddenComposer());
		}

		message.setHabboHidder(connection.getHabbo());
		message.setHidden(hidden);
		message.save();

		connection.send(new GuildForumMessageUpdateComposer(message));
	}

}
