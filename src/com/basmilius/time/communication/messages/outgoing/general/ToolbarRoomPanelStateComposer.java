package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ToolbarRoomPanelStateComposer extends MessageComposer
{

	private final boolean collapsed;

	public ToolbarRoomPanelStateComposer(final boolean collapsed)
	{
		this.collapsed = collapsed;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ToolbarRoomPanelState);
		response.appendInt(this.collapsed ? 1 : 0);
		return response;
	}

}
