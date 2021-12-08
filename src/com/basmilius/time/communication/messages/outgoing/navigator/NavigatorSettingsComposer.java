package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorSettingsComposer extends MessageComposer
{

	private final int int1;
	private final int int2;
	private final int int3;
	private final int int4;
	private final boolean savedSearchesCollapsed;
	private final int int5;

	public NavigatorSettingsComposer(final int int1, final int int2, final int int3, final int int4, final boolean savedSearchesCollapsed, final int int5)
	{
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
		this.int4 = int4;
		this.savedSearchesCollapsed = savedSearchesCollapsed;
		this.int5 = int5;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorSettings);
		response.appendInt(this.int1);
		response.appendInt(this.int2);
		response.appendInt(this.int3);
		response.appendInt(this.int4);
		response.appendBoolean(this.savedSearchesCollapsed);
		response.appendInt(this.int5);
		return response;
	}

}
