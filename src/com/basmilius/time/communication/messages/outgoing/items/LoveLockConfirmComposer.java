package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class LoveLockConfirmComposer extends MessageComposer
{

	private final int itemId;
	private final boolean showItem;

	public LoveLockConfirmComposer(final int itemId, final boolean showItem)
	{
		this.itemId = itemId;
		this.showItem = showItem;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.LoveLockConfirm);
		response.appendInt(this.itemId);
		response.appendBoolean(this.showItem);
		return response;
	}

}
