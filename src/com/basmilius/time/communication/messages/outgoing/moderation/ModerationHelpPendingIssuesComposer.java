package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.moderation.collections.ModerationIssueList;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationHelpPendingIssuesComposer extends MessageComposer
{

	private ModerationIssueList issues;

	public ModerationHelpPendingIssuesComposer(ModerationIssueList issues)
	{
		this.issues = issues;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationHelpPendingIssues);
		response.appendInt(this.issues.size());
		for (final ModerationIssue issue : this.issues)
		{
			response.appendString(issue.getId());
			response.appendString(issue.getCreatedOn());
			response.appendString(issue.getMessage());
		}
		return response;
	}

}
