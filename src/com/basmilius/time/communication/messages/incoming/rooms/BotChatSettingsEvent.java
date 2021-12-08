package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.BotSettingsComposer;

@Event(id = Incoming.BotChatSettings)
public class BotChatSettingsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int botId = Math.abs(packet.readInt());
		final int settingId = packet.readInt();

		final Bot bot = Bootstrap.getEngine().getGame().getBotManager().getBot(botId);

		if (bot == null)
			return; // Bot doesn't exists

		connection.send(new BotSettingsComposer(settingId, bot));
	}

}
