package com.basmilius.time.communication.messages.outgoing.rooms.games;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FreezeLifesComposer extends MessageComposer
{

	private final int _unitId;
	private final int _lifes;

	public FreezeLifesComposer(int _unitId, int _lifes)
	{
		this._unitId = _unitId;
		this._lifes = _lifes;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FreezeLifes);
		response.appendInt(this._unitId);
		response.appendInt(this._lifes);
		return response;
	}

}
