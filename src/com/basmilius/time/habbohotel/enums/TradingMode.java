package com.basmilius.time.habbohotel.enums;

public enum TradingMode
{
	NOBODY_CAN_TRADE,
	ONLY_ROOM_OWNER,
	EVERYONE_CAN_TRADE;

	public static TradingMode fromInt(int mode)
	{
		if (mode == 0)
			return NOBODY_CAN_TRADE;
		else if (mode == 1)
			return ONLY_ROOM_OWNER;
		else
			return EVERYONE_CAN_TRADE;
	}

	public int asInt()
	{
		if (this == NOBODY_CAN_TRADE)
			return 0;
		else if (this == ONLY_ROOM_OWNER)
			return 1;
		else
			return 2;
	}
}
