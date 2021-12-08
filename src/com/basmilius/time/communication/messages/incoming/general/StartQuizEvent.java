package com.basmilius.time.communication.messages.incoming.general;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.general.QuizDataComposer;

@Event(id = Incoming.StartQuiz)
public class StartQuizEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String quiz = packet.readString();

		if (quiz.equals("SafetyQuiz1"))
		{
			connection.getHabbo().getAchievements().unlockAchievement("ACH_SafetyQuizGraduate", 1);
			return;
		}

		connection.send(new QuizDataComposer());
	}

}
