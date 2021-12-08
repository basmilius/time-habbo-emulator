package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationIssueChatlogsComposer;

@Event(id = Incoming.ModerationIssueChatlogs)
public class ModerationIssueChatlogsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		final int issueId = packet.readInt();
		final ModerationIssue issue = Bootstrap.getEngine().getGame().getModerationManager().getIssue(issueId);

		connection.send(new ModerationIssueChatlogsComposer(issue));
	}

}
