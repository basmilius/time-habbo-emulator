package com.basmilius.time.communication.messages.incoming.quests;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.QuestsList)
public class QuestsListEvent extends MessageEvent
{
	
	@Override
	public final void handle()
	{
	}
	
}
