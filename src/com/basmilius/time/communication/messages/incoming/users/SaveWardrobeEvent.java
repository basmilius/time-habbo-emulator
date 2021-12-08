package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SaveWardrobe)
public class SaveWardrobeEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int slotId = packet.readInt();
		String figure = packet.readString();
		String gender = packet.readString();

		connection.getHabbo().getSettings().getWardrobe().setSlotData(slotId, figure, gender);
	}

}
