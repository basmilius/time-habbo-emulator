package com.basmilius.time.habbohotel.enums;


public enum DoorMode
{

	OPEN,
	DOORBELL,
	PASSWORD,
	INVISIBLE;

	public static DoorMode fromInt(int doorMode)
	{
		if (doorMode == 0)
			return OPEN;
		else if (doorMode == 1)
			return DOORBELL;
		else if (doorMode == 2)
			return PASSWORD;
		else if (doorMode == 3)
			return INVISIBLE;
		return OPEN;
	}

	public int asInt()
	{
		if (this == OPEN)
			return 0;
		else if (this == DOORBELL)
			return 1;
		else if (this == PASSWORD)
			return 2;
		else if (this == INVISIBLE)
			return 3;
		return 0;
	}

}
