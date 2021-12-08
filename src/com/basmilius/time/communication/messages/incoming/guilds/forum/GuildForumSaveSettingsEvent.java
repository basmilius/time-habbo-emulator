package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumGuildDataComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.LocalizedNotificationComposer;

@Event(id = Incoming.GuildForumSaveSettings)
public class GuildForumSaveSettingsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int readLevel = packet.readInt();
		final int replyLevel = packet.readInt();
		final int topicLevel = packet.readInt();
		final int moderateLevel = packet.readInt();

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		if (guild.getHabbo().getId() != connection.getHabbo().getId())
			return;

		if (!guild.isForumActive())
			return;

		guild.setForumReadLevel(readLevel);
		guild.setForumReplyLevel(replyLevel);
		guild.setForumTopicLevel(topicLevel);
		guild.setForumModerateLevel(moderateLevel);
		guild.save();

		connection.send(new GuildForumGuildDataComposer(guild, connection.getHabbo()));
		connection.send(new LocalizedNotificationComposer.ForumSettingsUpdatedComposer());
	}

}
