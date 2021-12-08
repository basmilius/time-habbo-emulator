package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.habbohotel.moderation.ModerationIssueChatEntry;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.ModerationReportUser)
public class ModerationReportUserEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String message = packet.readString();
		final int category = packet.readInt();
		final int reportedHabboId = packet.readInt();
		final int roomId = packet.readInt();
		final int chatLinesCount = packet.readInt();

		final List<ModerationIssueChatEntry> chatLines = new ArrayList<>();

		int i = 0;
		while (i < chatLinesCount)
		{
			chatLines.add(new ModerationIssueChatEntry(packet.readInt(), packet.readString()));
			i++;
		}
	}

}
