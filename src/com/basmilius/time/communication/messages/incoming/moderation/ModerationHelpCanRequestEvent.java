package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.moderation.collections.ModerationIssueList;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationHelpCanRequestComposer;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationHelpPendingIssuesComposer;

@Event(id = Incoming.ModerationHelpCanRequest)
public class ModerationHelpCanRequestEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final ModerationIssueList issues = Bootstrap.getEngine().getGame().getModerationManager().getIssues(connection.getHabbo());

		if (issues.size() > 0)
		{
			connection.send(new ModerationHelpPendingIssuesComposer(issues));
			return;
		}

		connection.send(new ModerationHelpCanRequestComposer());
	}

}
