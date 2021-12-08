package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationIssueChatlogsComposer extends MessageComposer
{

	private final ModerationIssue issue;

	public ModerationIssueChatlogsComposer(final ModerationIssue issue)
	{
		this.issue = issue;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationIssueChatlogs);
		this.issue.serializeChatlog(response);
		return response;
	}

}
