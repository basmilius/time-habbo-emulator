package com.basmilius.time.habbohotel.pets.race;

public class PetRace
{

	private final int petId;
	private final int colorOne;
	private final int colorTwo;
	private final boolean colorOneEnabled;
	private final boolean colorTwoEnabled;

	public PetRace(final int petId, final int colorOne, final int colorTwo, final boolean colorOneEnabled, final boolean colorTwoEnabled)
	{
		this.petId = petId;
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
		this.colorOneEnabled = colorOneEnabled;
		this.colorTwoEnabled = colorTwoEnabled;
	}

	public final int getPetId()
	{
		return this.petId;
	}

	public final int getColorOne()
	{
		return this.colorOne;
	}

	public final int getColorTwo()
	{
		if (!this.isColorTwoEnabled())
			return this.colorOne;
		return this.colorTwo;
	}

	public final boolean isColorOneEnabled()
	{
		return this.colorOneEnabled;
	}

	public final boolean isColorTwoEnabled()
	{
		return this.colorTwoEnabled;
	}
}
