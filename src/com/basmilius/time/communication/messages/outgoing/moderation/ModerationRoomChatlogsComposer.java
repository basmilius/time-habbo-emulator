package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.moderation.chatlog.Chatlog;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class ModerationRoomChatlogsComposer extends MessageComposer
{

	private Room room;
	private List<Chatlog> chatlogs;

	public ModerationRoomChatlogsComposer(Room room, List<Chatlog> chatlogs)
	{
		this.room = room;
		this.chatlogs = chatlogs;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationRoomChatlogs);
		response.appendByte(1);
		response.appendShort(2);
		response.appendString("roomId");
		response.appendByte(1);
		response.appendInt(this.room.getRoomData().getId());
		response.appendString("roomName");
		response.appendByte(2);
		response.appendString(this.room.getRoomData().getRoomName());
		response.appendShort(this.chatlogs.size());
		for (Chatlog chatlog : this.chatlogs)
		{
			chatlog.serialize(response);
		}
		return response;
	}

}
