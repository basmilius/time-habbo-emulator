package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumMessage;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumNewMessageComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumNewThreadComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.LocalizedNotificationComposer;

@Event(id = Incoming.GuildForumCreatePost)
public class GuildForumCreatePostEvent extends MessageEvent
{

	@SuppressWarnings("ConstantConditions")
	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int threadId = packet.readInt();
		final String subject = packet.readString();
		final String message = packet.readString();

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);
		final boolean isThread = (threadId == 0);

		if (guild == null)
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}
		else if (!guild.isForumActive())
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}
		else if (guild.getForumReadLevel() > (guild.getHasMember(connection.getHabbo()) ? guild.getMemberByHabbo(connection.getHabbo()).getLevelID() : 0))
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}
		else if (isThread && subject.length() < 10)
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}
		else if (message.length() < 10)
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}
		else if (!isThread && guild.getGuildForum().getThread(threadId) != null && guild.getGuildForum().getThread(threadId).isLocked())
		{
			connection.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
			return;
		}

		final String subjectFiltered = Bootstrap.getEngine().getGame().getWordFilterManager().filterString(subject);
		final String messageFiltered = Bootstrap.getEngine().getGame().getWordFilterManager().filterString(message);

		if (isThread)
		{
			final GuildForumThread thread = guild.getGuildForum().postThread(connection.getHabbo(), subjectFiltered, messageFiltered);
			connection.send(new GuildForumNewThreadComposer(thread));
		}
		else
		{
			final GuildForumMessage post = guild.getGuildForum().getThread(threadId).postMessage(connection.getHabbo(), messageFiltered);
			connection.send(new GuildForumNewMessageComposer(post));
		}
	}

}