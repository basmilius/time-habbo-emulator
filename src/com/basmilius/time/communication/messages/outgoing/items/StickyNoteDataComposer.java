package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class StickyNoteDataComposer extends MessageComposer
{

	private final UserItem item;

	public StickyNoteDataComposer(UserItem item)
	{
		this.item = item;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.StickyNoteData);
		response.appendString(Integer.toString(this.item.getId()));
		response.appendString((this.item.getExtraData().isEmpty()) ? "FFFF33" : this.item.getExtraData());
		return response;
	}

}
