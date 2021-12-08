package com.basmilius.time.communication.messages.outgoing.soundmachine;

import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.items.soundmachine.Song;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.ArrayList;
import java.util.List;

public class SongInfoComposer extends MessageComposer
{

	private final List<Song> songs;

	public SongInfoComposer(final List<Song> songs)
	{
		this.songs = songs;
	}

	public SongInfoComposer(Song song)
	{
		this.songs = new ArrayList<>();
		this.songs.add(song);
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SongInfo);
		response.appendInt(this.songs.size());
		for (final Song song : this.songs)
		{
			response.appendInt(song.getId());
			response.appendString(StringCrypt.getSHA1Hash(Integer.toString(song.getId())));
			response.appendString(song.getName());
			response.appendString(song.getTrack());
			response.appendInt(song.getLength() * 1000);
			response.appendString(song.getAuthor());
		}
		return response;
	}

}
