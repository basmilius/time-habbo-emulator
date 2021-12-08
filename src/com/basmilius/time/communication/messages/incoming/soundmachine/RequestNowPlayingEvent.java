package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.cycles.SoundMachineCycle;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SoundMachineNowPlayingComposer;

@Event(id = Incoming.RequestNowPlaying)
public class RequestNowPlayingEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		if (room.getCycle(SoundMachineCycle.class).getCurrentCd() != null)
		{
			connection.send(new SoundMachineNowPlayingComposer(room.getCycle(SoundMachineCycle.class).getCurrentCd().getCatalogItem().getSongId(), 0, room.getCycle(SoundMachineCycle.class).getStartTime()));
		}
	}

}
