package com.basmilius.time.habbohotel.wordfilter;

public final class WordEntry
{

	private final Word word;
	private final int startIndex;
	private final int endIndex;

	public WordEntry(final Word word, final int startIndex, final int endIndex)
	{
		this.word = word;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
	}

	public final Word getWord()
	{
		return this.word;
	}

	public final int getStartIndex()
	{
		return this.startIndex;
	}

	public final int getEndIndex()
	{
		return this.endIndex;
	}
}
