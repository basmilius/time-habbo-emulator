package com.basmilius.time.communication.messages.outgoing.poll;

import com.basmilius.time.habbohotel.poll.Poll;
import com.basmilius.time.habbohotel.poll.PollAnswer;
import com.basmilius.time.habbohotel.poll.PollQuestion;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PollContentsComposer extends MessageComposer
{

	private int i;
	private Poll poll;

	public PollContentsComposer(Poll poll)
	{
		this.i = 0;
		this.poll = poll;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PollContents);
		response.appendInt(this.poll.getId());
		response.appendString(this.poll.getDescription());
		response.appendString(this.poll.getMessageDone());
		response.appendInt(this.poll.getQuestionsCount());

		for (final PollQuestion question : this.poll.getQuestions())
		{
			this.i++;
			response.appendInt(question.getId());
			response.appendInt(this.i);
			response.appendInt(question.getAnswerType());
			response.appendString(question.getQuestion());

			if (question.getAnswerType() == 1 || question.getAnswerType() == 2)
			{
				response.appendInt(1); // Minimal answers.
				response.appendInt(1); // Minimal answers.
				response.appendInt(question.getAnswers().size());

				for (PollAnswer answer : question.getAnswers())
				{
					response.appendString(Integer.toString(answer.getId()));
					response.appendString(answer.getAnswer());
					response.appendInt(question.getAnswerType());
				}
			}
			response.appendInt(0);
		}

		response.appendBoolean(true);
		return response;
	}

}
