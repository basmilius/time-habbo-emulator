package com.basmilius.time.communication.messages.incoming.preferences;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetChatPreferences)
public class SetChatPreferencesMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		boolean yes = packet.readBoolean();

		connection.getHabbo().getSettings().setPreferOldChat(yes);
	}

}
