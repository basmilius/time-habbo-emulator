package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ModerationReportGuildForumThread)
public class ModerationReportGuildForumThreadEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int threadId = packet.readInt();
		final int category = packet.readInt();
		final String message = packet.readString();

		Bootstrap.getEngine().getGame().getModerationManager().createGuildThreadIssue(connection.getHabbo(), category, guildId, threadId, message);
	}

}
