package com.basmilius.time.communication.messages.outgoing.soundmachine;

import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.items.soundmachine.Song;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SongIdComposer extends MessageComposer
{

	private Song song;

	public SongIdComposer(Song song)
	{
		this.song = song;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SongId);
		response.appendString(StringCrypt.getSHA1Hash(Integer.toString(this.song.getId())));
		response.appendInt(this.song.getId());
		return response;
	}

}
