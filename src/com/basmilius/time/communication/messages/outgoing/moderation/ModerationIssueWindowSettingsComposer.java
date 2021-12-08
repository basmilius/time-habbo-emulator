package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationIssueWindowSettingsComposer extends MessageComposer
{

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	public ModerationIssueWindowSettingsComposer(final int x, final int y, final int width, final int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationIssueWindowSettings);
		response.appendInt(this.x);
		response.appendInt(this.y);
		response.appendInt(this.width);
		response.appendInt(this.height);
		return response;
	}

}
