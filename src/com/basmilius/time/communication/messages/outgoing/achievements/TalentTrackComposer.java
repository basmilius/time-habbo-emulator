package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TalentTrackComposer extends MessageComposer
{

	private final ISerialize _talentTrack;

	public TalentTrackComposer(ISerialize _talentTrack)
	{
		this._talentTrack = _talentTrack;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.TalentTrack);
		this._talentTrack.serialize(response);
		return response;
	}

}
