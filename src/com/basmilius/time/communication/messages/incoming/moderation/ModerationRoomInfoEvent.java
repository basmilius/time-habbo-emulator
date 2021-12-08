package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationRoomInfoComposer;

@Event(id = Incoming.ModerationRoomInfo)
public class ModerationRoomInfoEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		connection.send(new ModerationRoomInfoComposer(Bootstrap.getEngine().getGame().getModerationManager().getRoomInfo(packet.readInt())));
	}

}
