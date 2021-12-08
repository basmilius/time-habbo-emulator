package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomSettingsEnforceNewCategoryComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomSettingsEnforceNewCategory);
		response.appendInt(2); // ?? 1 = crash, 2 = ok, 3 = crash when save.
		return response;
	}

}
