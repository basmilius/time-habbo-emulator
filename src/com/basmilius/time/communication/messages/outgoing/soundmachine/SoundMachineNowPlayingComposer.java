package com.basmilius.time.communication.messages.outgoing.soundmachine;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SoundMachineNowPlayingComposer extends MessageComposer
{

	private final int index;
	private int songId;
	private int startTime;

	public SoundMachineNowPlayingComposer(final int songId, final int index, final int startTime)
	{
		this.songId = songId;
		this.index = index;
		this.startTime = startTime;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SoundMachineNowPlaying);
		response.appendInt(this.songId);
		response.appendInt(this.index);
		response.appendInt(this.songId);
		response.appendInt(0);
		response.appendInt(this.startTime);
		return response;
	}

}
