package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationActionResultComposer;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationReplyComposer;

@Event(id = Incoming.ModerationSendMessage)
public class ModerationSendMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		final int userId = packet.readInt();
		final String message = packet.readString();

		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(userId);

		if (!habbo.isOnline())
		{
			connection.send(new ModerationActionResultComposer(userId, false));
			return;
		}

		habbo.getConnection().send(new ModerationReplyComposer(message));
		connection.send(new ModerationActionResultComposer(userId, true));
	}

}
