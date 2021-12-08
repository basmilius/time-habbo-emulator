package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.soundmachine.Song;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SongInfoComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.RequestSongInfo)
public class RequestSongInfoEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int songCount = packet.readInt();
		final List<Song> songs = new ArrayList<>();

		for (int i = 0; i < songCount; i++)
		{
			final Song song = Bootstrap.getEngine().getGame().getSoundMachineManager().getSongById(packet.readInt());

			if (song == null)
				continue;

			songs.add(song);
		}

		connection.send(new SongInfoComposer(songs));
	}

}
