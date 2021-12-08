package com.basmilius.time.habbohotel.wordfilter;

public final class Word
{

	private final String word;
	private final String replacement;

	public Word(final String word, final String replacement)
	{
		this.word = word;
		this.replacement = replacement;
	}

	public final String filterString(final String input)
	{
		String output = input;
		output = output.replaceAll("(?i)" + this.word, this.replacement);
		return output;
	}

	public final String getWord()
	{
		return this.word;
	}

	public final boolean containsWord(final String input)
	{
		return input.toLowerCase().contains(this.word.toLowerCase());
	}

}
