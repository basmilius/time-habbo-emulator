package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class ModerationIssueFailedComposer extends MessageComposer
{

	private List<ModerationIssue> issues;
	private boolean retryEnabled;
	private int retryCount;

	public ModerationIssueFailedComposer(List<ModerationIssue> issues, boolean retryEnabled, int retryCount)
	{
		this.issues = issues;
		this.retryEnabled = retryEnabled;
		this.retryCount = retryCount;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationIssueFailed);
		response.appendInt(this.issues.size());
		for (final ModerationIssue issue : this.issues)
		{
			response.appendInt(issue.getId());
			response.appendInt(issue.getHabboReporterId());
			response.appendString(Bootstrap.getEngine().getGame().getHabboManager().getHabbo(issue.getHabboReporterId()).getUsername());
		}
		response.appendBoolean(this.retryEnabled);
		response.appendInt(this.retryCount);
		return response;
	}

}
