package com.basmilius.time.communication.messages.incoming.poll;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.poll.Poll;
import com.basmilius.time.habbohotel.poll.PollQuestion;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.PollAnswer)
public class PollAnswerEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<String> answers = new ArrayList<>();

		final int pollId = packet.readInt();
		final int questionId = packet.readInt();
		int countAnswers = packet.readInt();

		while (countAnswers >= 0)
		{
			answers.add(packet.readString());
			countAnswers--;
		}
		final Poll poll = Bootstrap.getEngine().getGame().getPollManager().getPoll(pollId);

		if (poll == null)
			return;

		final PollQuestion question = poll.getQuestion(questionId);

		if (question == null)
			return;

		// TODO: Handle poll data and save it into our database. Also give the user the rewards.

		question.getAnswers();
	}

}
