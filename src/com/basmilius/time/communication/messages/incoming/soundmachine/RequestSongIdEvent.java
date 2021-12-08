package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.soundmachine.Song;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SongIdComposer;

@Event(id = Incoming.RequestSongId)
public class RequestSongIdEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String sha1Name = packet.readString();
		final Song song = Bootstrap.getEngine().getGame().getSoundMachineManager().getSongBySha1Id(sha1Name);

		if (song == null)
			return;

		connection.send(new SongIdComposer(song));
	}

}
