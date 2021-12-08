package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CitizenshipComposer extends MessageComposer
{

	private final String _name;
	private final Habbo _habbo;

	public CitizenshipComposer(String _name, Habbo _habbo)
	{
		this._name = _name;
		this._habbo = _habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Citizenship);
		response.appendString(this._name);
		response.appendInt(this._habbo.getSettings().getCitizenLevel());
		response.appendInt(4);
		return response;
	}

}
