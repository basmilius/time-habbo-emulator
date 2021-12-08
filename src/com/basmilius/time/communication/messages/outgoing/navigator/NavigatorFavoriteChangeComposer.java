package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorFavoriteChangeComposer extends MessageComposer
{

	private int roomId;
	private boolean isFavorite;

	public NavigatorFavoriteChangeComposer(int roomId, boolean isFavorite)
	{
		this.roomId = roomId;
		this.isFavorite = isFavorite;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorFavoriteChange);
		response.appendInt(this.roomId);
		response.appendBoolean(this.isFavorite);
		return response;
	}

}
