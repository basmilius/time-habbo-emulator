package com.basmilius.time.habbohotel.poll;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.rooms.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PollManager extends IManager
{

	private List<Poll> polls;

	public PollManager()
	{
		this.polls = new ArrayList<>();
	}

	public Poll getPoll(int id)
	{
		for (Poll poll : this.polls)
			if (poll.getId() == id)
				return poll;

		return null;
	}

	public Poll getPollForRoom(Room room)
	{
		for (Poll poll : this.polls)
			if (poll.getRoomId() == room.getRoomData().getId())
				return poll;

		return null;
	}

	@Override
	public void initialize() throws Exception
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(PollManager.class, "Loading room polls .. ");

		PreparedStatement statementPoll = Bootstrap.getEngine().getDatabase().prepare("SELECT id, room_id, description, message_done, reward_badge, reward_credits, reward_duckets, reward_furni FROM poll WHERE enabled = '1'");
		{
			ResultSet result = statementPoll.executeQuery();

			while (result.next())
			{
				this.polls.add(new Poll(result));
			}
		}

		PreparedStatement statementPollQuestions = Bootstrap.getEngine().getDatabase().prepare("SELECT id, poll_id, question, answer_type FROM poll_questions");
		{
			ResultSet result = statementPollQuestions.executeQuery();

			while (result.next())
			{
				int pollId = result.getInt("poll_id");
				Poll poll = this.getPoll(pollId);

				if (poll == null)
					continue;

				poll.addQuestion(new PollQuestion(result));
			}
		}

		PreparedStatement statementPollAnswers = Bootstrap.getEngine().getDatabase().prepare("SELECT id, poll_id, question_id, answer FROM poll_answers");
		{
			ResultSet result = statementPollAnswers.executeQuery();

			while (result.next())
			{
				int pollId = result.getInt("poll_id");
				int questionId = result.getInt("question_id");
				Poll poll = this.getPoll(pollId);

				if (poll == null)
					continue;

				PollQuestion question = poll.getQuestion(questionId);

				if (question == null)
					continue;

				question.addAnswer(new PollAnswer(result));
			}
		}

		Bootstrap.getEngine().getLogging().logOK();
	}

	@Override
	public void dispose()
	{

	}

}
