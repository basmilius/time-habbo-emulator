package com.basmilius.time.communication.messages.incoming.preferences;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetSoundSettings)
public class SetSoundSettingsMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int systemVolume = packet.readInt();
		final int furniVolume = packet.readInt();
		final int traxVolume = packet.readInt();

		connection.getHabbo().getSettings().setVolumes(systemVolume, furniVolume, traxVolume);
	}

}
