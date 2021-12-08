package com.basmilius.time.habbohotel.enums;

public enum AvatarAction
{

	NONE,
	WAVE,
	BLOWKISS,
	LAUGH,
	JUMP,
	RESPECT;

	public static AvatarAction fromInt(int actionId)
	{
		switch (actionId)
		{
			case 1:
				return WAVE;
			case 2:
				return BLOWKISS;
			case 3:
				return LAUGH;
			case 6:
				return JUMP;
			case 7:
				return RESPECT;
			default:
				return NONE;
		}
	}

	public int asInt()
	{
		switch (this)
		{
			case WAVE:
				return 1;
			case BLOWKISS:
				return 2;
			case LAUGH:
				return 3;
			case JUMP:
				return 6;
			case RESPECT:
				return 7;
			case NONE:
			default:
				return 0;
		}
	}

}
