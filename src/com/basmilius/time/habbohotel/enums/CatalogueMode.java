package com.basmilius.time.habbohotel.enums;

public enum CatalogueMode
{

	BUILDERS_CLUB,
	BUILDER_NORMAL,
	NORMAL;

	public static CatalogueMode fromString(String mode)
	{
		if (mode.equalsIgnoreCase("builder") || mode.equalsIgnoreCase("builders_club"))
			return BUILDERS_CLUB;
		else if (mode.equalsIgnoreCase("builder_normal"))
			return BUILDER_NORMAL;

		return NORMAL;
	}

}