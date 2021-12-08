package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorFavoritesComposer extends MessageComposer
{

	private Integer[] roomIds;

	public NavigatorFavoritesComposer(Integer[] roomIds)
	{
		this.roomIds = roomIds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorFavorites);
		response.appendInt(Bootstrap.getEngine().getConfig().getInt("hotel.navigator.max.favorites", 50));
		response.appendInt(this.roomIds.length);
		for (Integer roomId : this.roomIds)
		{
			response.appendInt(roomId);
		}
		return response;
	}

}
