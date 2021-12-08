package com.basmilius.time.communication.messages.outgoing.poll;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PollSummaryComposer extends MessageComposer
{

	private int pollId;
	private String summary;

	public PollSummaryComposer(int pollId, String summary)
	{
		this.pollId = pollId;
		this.summary = summary;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PollSummary);
		response.appendInt(this.pollId);
		response.appendString("a");
		response.appendString("b");
		response.appendString(this.summary);
		return response;
	}

}
