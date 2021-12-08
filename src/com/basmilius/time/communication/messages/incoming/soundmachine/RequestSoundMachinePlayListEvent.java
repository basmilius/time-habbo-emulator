package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.furniture.SoundMachineUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SoundMachinePlayListComposer;

import java.util.List;

@Event(id = Incoming.RequestSoundMachinePlayList)
public class RequestSoundMachinePlayListEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		final List<SoundMachineUserItem> jukeboxes = connection.getHabbo().getCurrentRoom().getRoomObjectsHandler().getItems(SoundMachineUserItem.class);

		if (jukeboxes.size() == 0)
			return;

		final SoundMachineUserItem jukebox = jukeboxes.get(0);
		connection.send(new SoundMachinePlayListComposer(jukebox, Bootstrap.getEngine().getGame().getSoundMachineManager().getCdsForJukeBox(jukebox)));
	}

}
