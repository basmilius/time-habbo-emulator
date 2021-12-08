package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomSerializer;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class NavigatorSearchResultSetComposer extends MessageComposer
{

	private final String searchCode;
	private final String text;
	private final List<Room> rooms;

	public NavigatorSearchResultSetComposer(String searchCode, String text, List<Room> rooms)
	{
		this.searchCode = searchCode;
		this.text = text;
		this.rooms = rooms;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorSearchResultSet);
		response.appendString(this.searchCode);
		response.appendString(this.text);

		response.appendInt(2);

		response.appendString(this.searchCode);
		response.appendString(this.text);
		response.appendInt(0);
		response.appendBoolean(false); // show more enabled
		response.appendInt(-1);
		response.appendInt(this.rooms.size());
		for (final Room room : this.rooms)
		{
			response.appendBody(new RoomSerializer(room));
		}

		response.appendString(this.searchCode);
		response.appendString(this.text);
		response.appendInt(1);
		response.appendBoolean(true);
		response.appendInt(0);
		response.appendInt(this.rooms.size());
		for (final Room room : this.rooms)
		{
			response.appendBody(new RoomSerializer(room));
		}

		return response;
	}

}
