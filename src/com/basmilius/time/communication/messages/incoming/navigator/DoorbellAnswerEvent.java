package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.DoorbellRemoveComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.DoorbellRemoveUserComposer;

@Event(id = Incoming.DoorbellAnswer)
public class DoorbellAnswerEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String userName = packet.readString();
		final boolean mayEnter = packet.readBoolean();

		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(userName);

		if (habbo == null)
			return;

		if (mayEnter)
		{
			habbo.getConnection().send(new DoorbellRemoveComposer());
			Bootstrap.getEngine().getGame().getRoomManager().loadRoomForHabbo(habbo, connection.getHabbo().getCurrentRoom().getRoomData().getId(), "", true);
		}
		else
		{
			habbo.getConnection().send(new DoorbellRemoveUserComposer(""));
			connection.getHabbo().getCurrentRoom().getRoomUnitsHandler().send(new DoorbellRemoveUserComposer(habbo.getUsername()), true);
		}
	}

}
