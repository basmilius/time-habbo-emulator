package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.moderation.chatlog.Chatlog;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationUserChatlogsComposer;

import java.util.List;

@Event(id = Incoming.ModerationUserChatlogs)
public class ModerationUserChatlogsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		final int userId = packet.readInt();
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(userId);
		final List<Chatlog> chatlogs = Bootstrap.getEngine().getGame().getModerationManager().getChatlogs().getChatlogsFromHabbo(habbo);

		connection.send(new ModerationUserChatlogsComposer(habbo, chatlogs));
	}

}
