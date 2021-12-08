package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class UpdateLookComposer extends MessageComposer
{

	private final Habbo _habbo;

	public UpdateLookComposer(Habbo habbo)
	{
		this._habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateLook);
		response.appendString(this._habbo.getLook());
		response.appendString(this._habbo.getGender());
		return response;
	}

}
