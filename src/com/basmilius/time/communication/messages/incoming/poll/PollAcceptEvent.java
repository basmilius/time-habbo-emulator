package com.basmilius.time.communication.messages.incoming.poll;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.poll.Poll;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.poll.PollContentsComposer;

@Event(id = Incoming.PollAccept)
public class PollAcceptEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int pollId = packet.readInt();
		final Poll poll = Bootstrap.getEngine().getGame().getPollManager().getPoll(pollId);

		if (poll == null)
			return;

		connection.send(new PollContentsComposer(poll));
	}

}
