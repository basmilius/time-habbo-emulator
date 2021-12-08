package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RemoveEffectItemComposer extends MessageComposer
{

	private int effectId;

	public RemoveEffectItemComposer(int effectId)
	{
		this.effectId = effectId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RemoveEffectItem);
		response.appendInt(this.effectId);
		return response;
	}

}
