package com.basmilius.time.habbohotel.poll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Poll
{

	private int id;
	private int roomId;
	private String description;
	private String messageDone;

	private List<PollQuestion> questions;

	public Poll(ResultSet result) throws SQLException
	{
		this.questions = new ArrayList<>();

		this.id = result.getInt("id");
		this.roomId = result.getInt("room_id");
		this.description = result.getString("description");
		this.messageDone = result.getString("message_done");
	}

	public void addQuestion(PollQuestion question)
	{
		this.questions.add(question);
	}

	public PollQuestion getQuestion(int id)
	{
		for (PollQuestion question : this.questions)
			if (question.getId() == id)
				return question;

		return null;
	}

	public List<PollQuestion> getQuestions()
	{
		return this.questions;
	}

	public int getQuestionsCount()
	{
		return this.questions.size();
	}

	public int getId()
	{
		return this.id;
	}

	public int getRoomId()
	{
		return this.roomId;
	}

	public String getDescription()
	{
		return this.description;
	}

	public String getMessageDone()
	{
		return this.messageDone;
	}

}
