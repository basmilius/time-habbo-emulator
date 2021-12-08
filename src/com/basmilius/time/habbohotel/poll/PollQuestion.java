package com.basmilius.time.habbohotel.poll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PollQuestion
{

	private int id;
	private String question;
	private int answerType;

	private List<PollAnswer> answers;

	public PollQuestion(ResultSet result) throws SQLException
	{
		this.answers = new ArrayList<>();

		this.answerType = result.getInt("answer_type");
		this.id = result.getInt("id");
		this.question = result.getString("question");
	}

	public void addAnswer(PollAnswer answer)
	{
		this.answers.add(answer);
	}

	public List<PollAnswer> getAnswers()
	{
		return this.answers;
	}

	public int getAnswerType()
	{
		return this.answerType;
	}

	public int getId()
	{
		return this.id;
	}

	public String getQuestion()
	{
		return this.question;
	}

}
