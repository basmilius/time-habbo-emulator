package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomCategory;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomCategoriesComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomCategories);
		response.appendInt(Bootstrap.getEngine().getGame().getRoomManager().getCategories().size());
		for (final RoomCategory category : Bootstrap.getEngine().getGame().getRoomManager().getCategories())
		{
			response.appendInt(category.getId());
			response.appendString(category.getCaption());
			response.appendBoolean(true); // Show this category
			response.appendBoolean(false);
			response.appendString("");
			response.appendString("");
			response.appendBoolean(false);
		}
		return response;
	}

}
