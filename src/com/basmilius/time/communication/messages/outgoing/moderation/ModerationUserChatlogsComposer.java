package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.moderation.chatlog.Chatlog;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.Collections;
import java.util.List;

public class ModerationUserChatlogsComposer extends MessageComposer
{

	private final Habbo habbo;
	private final List<Chatlog> chatlogs;

	public ModerationUserChatlogsComposer(final Habbo habbo, final List<Chatlog> chatlogs)
	{
		this.habbo = habbo;
		this.chatlogs = chatlogs;

		Collections.sort(this.chatlogs, (o1, o2) -> (o1.getId() < o2.getId()) ? 1 : -1);
	}

	@Override
	public final ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationUserChatlogs);
		response.appendInt(this.habbo.getId());
		response.appendString(this.habbo.getUsername());
		response.appendInt(1);

		response.appendByte(1);

		if (this.habbo.isInRoom())
		{
			response.appendShort(2);
			response.appendString("roomId");
			response.appendByte(1);
			response.appendInt(this.habbo.getCurrentRoom().getRoomData().getId());
			response.appendString("roomName");
			response.appendByte(2);
			response.appendString(this.habbo.getCurrentRoom().getRoomData().getRoomName());
		}
		else
		{
			response.appendShort(0);
		}

		response.appendShort(this.chatlogs.size());
		for (final Chatlog chatlog : this.chatlogs)
		{
			chatlog.serialize(response);
		}
		return response;
	}

}
