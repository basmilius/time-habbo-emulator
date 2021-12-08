package com.basmilius.time.communication.messages.incoming.preferences;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.HabboSettingsComposer;

@Event(id = Incoming.SetChatStylePreference)
public class SetChatStylePreferenceMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int chatBubble = packet.readInt();

		connection.getHabbo().getSettings().setLatestChatBubble(chatBubble);
		connection.send(new HabboSettingsComposer(connection.getHabbo()));
	}

}
