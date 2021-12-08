package com.basmilius.time.habbohotel.wordfilter;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordFilterManager extends IManager
{

	private final List<Word> words;

	public WordFilterManager()
	{
		this.words = new ArrayList<>();
	}

	public final String filterString(String input)
	{
		String output = input;

		for (final Word word : this.words)
		{
			output = word.filterString(output);
		}

		return output;
	}

	public final List<WordEntry> getWordEntriesForString(final String sequence)
	{
		List<WordEntry> words = new ArrayList<>();

		if (this.stringContainsBadWord(sequence))
		{
			this.words.stream().filter(word -> word.containsWord(sequence)).forEach(word -> {
				final int index = sequence.indexOf(word.getWord());
				words.add(new WordEntry(word, index, (index + word.getWord().length())));
			});
		}

		return words;
	}

	public boolean stringContainsBadWord(String input)
	{
		boolean containsBadWord = false;

		for (final Word word : this.words)
		{
			if (containsBadWord)
				break;

			containsBadWord = word.containsWord(input);
		}

		return containsBadWord;
	}

	@Override
	public void initialize()
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(WordFilterManager.class, "Loading wordfilter .. ");

		try
		{
			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM wordfilter");
			{
				ResultSet result = statement.executeQuery();

				while (result.next())
				{
					final String[] words = result.getString("words").split(",");
					for (final String word : words)
					{
						this.words.add(new Word(word, result.getString("replacement")));
					}
				}
			}

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	@Override
	public void dispose()
	{
		this.words.clear();
	}

}
