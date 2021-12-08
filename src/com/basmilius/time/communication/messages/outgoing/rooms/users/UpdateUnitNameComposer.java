package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class UpdateUnitNameComposer extends MessageComposer
{

	private final int _unitId;
	private final String _newName;

	public UpdateUnitNameComposer(int _unitId, String _newName)
	{
		this._unitId = _unitId;
		this._newName = _newName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.UpdateUnitName);
		response.appendInt(-1);
		response.appendInt(this._unitId);
		response.appendString(this._newName);
		return response;
	}

}
