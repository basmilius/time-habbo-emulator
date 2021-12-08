package com.basmilius.time.habbohotel.poll;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PollAnswer
{

	private String answer;
	private int id;
	private int pollId;
	private int questionId;

	public PollAnswer(ResultSet result) throws SQLException
	{
		this.answer = result.getString("answer");
		this.id = result.getInt("id");
		this.pollId = result.getInt("poll_id");
		this.questionId = result.getInt("question_id");
	}

	public String getAnswer()
	{
		return this.answer;
	}

	public int getId()
	{
		return this.id;
	}

	public int getPollId()
	{
		return this.pollId;
	}

	public int getQuestionId()
	{
		return this.questionId;
	}
}
