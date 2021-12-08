package com.basmilius.time.habbohotel.enums;

public enum ChatBubble
{

	DEFAULT, // 0
	EXCLAMATION, // 1
	BOT, // 2
	RED, // 3
	BLUE, // 4
	YELLOW, // 5
	GREEN, // 6
	GREY, // 7
	HABBOWHEEN_ONE, // 8
	HABBOWHEEN_TWO, // 9
	HABBOWHEEN_THREE, // 10
	LIGHT_BLUE, // 11
	PINK, // 12
	PURPLE, // 13
	GOLD, // 14
	DARK_BLUE, // 15
	HEARTS, // 16
	FLOWERS, // 17
	PIG, // 19
	DOG, // 20
	PACMAN, // 21
	DRAGON, // 22
	STAFF, // 23
	BATS, // 24
	CONSOLE, // 25
	PIRATES_ONE, // 26
	PIRATES_TWO, // 27
	PIRATES_THREE, // 28
	PIRATES_FOUR, // 29,
	BOT_LIGHT, // 30
	BOT_DARK, // 31
	DEATH, // 32
	FRANK_BOT, // 33
	WIRED;

	public static ChatBubble fromInt(int bubbleId)
	{
		switch (bubbleId)
		{
			case 0:
				return DEFAULT;
			case 1:
				return EXCLAMATION;
			case 2:
				return BOT;
			case 3:
				return RED;
			case 4:
				return BLUE;
			case 5:
				return YELLOW;
			case 6:
				return GREEN;
			case 7:
				return GREY;
			case 8:
				return HABBOWHEEN_ONE;
			case 9:
				return HABBOWHEEN_TWO;
			case 10:
				return HABBOWHEEN_THREE;
			case 11:
				return LIGHT_BLUE;
			case 12:
				return PINK;
			case 13:
				return PURPLE;
			case 14:
				return GOLD;
			case 15:
				return DARK_BLUE;
			case 16:
				return HEARTS;
			case 17:
				return FLOWERS;
			case 19:
				return PIG;
			case 20:
				return DOG;
			case 21:
				return PACMAN;
			case 22:
				return DRAGON;
			case 23:
				return STAFF;
			case 24:
				return BATS;
			case 25:
				return CONSOLE;
			case 26:
				return PIRATES_ONE;
			case 27:
				return PIRATES_TWO;
			case 28:
				return PIRATES_THREE;
			case 29:
				return PIRATES_FOUR;
			case 30:
				return BOT_LIGHT;
			case 31:
				return BOT_DARK;
			case 32:
				return DEATH;
			case 33:
				return FRANK_BOT;
			case 34:
				return WIRED;
		}
		return DEFAULT;
	}

	public int toInt()
	{
		switch (this)
		{
			case DEFAULT:
				return 0;
			case EXCLAMATION:
				return 1;
			case BOT:
				return 2;
			case RED:
				return 3;
			case BLUE:
				return 4;
			case YELLOW:
				return 5;
			case GREEN:
				return 6;
			case GREY:
				return 7;
			case HABBOWHEEN_ONE:
				return 8;
			case HABBOWHEEN_TWO:
				return 9;
			case HABBOWHEEN_THREE:
				return 10;
			case LIGHT_BLUE:
				return 11;
			case PINK:
				return 12;
			case PURPLE:
				return 13;
			case GOLD:
				return 14;
			case DARK_BLUE:
				return 15;
			case HEARTS:
				return 16;
			case FLOWERS:
				return 17;
			case PIG:
				return 19;
			case DOG:
				return 20;
			case PACMAN:
				return 21;
			case DRAGON:
				return 22;
			case STAFF:
				return 23;
			case BATS:
				return 24;
			case CONSOLE:
				return 25;
			case PIRATES_ONE:
				return 26;
			case PIRATES_TWO:
				return 27;
			case PIRATES_THREE:
				return 28;
			case PIRATES_FOUR:
				return 29;
			case BOT_LIGHT:
				return 30;
			case BOT_DARK:
				return 31;
			case DEATH:
				return 32;
			case FRANK_BOT:
				return 33;
			case WIRED:
				return 34;
		}
		return 0;
	}

}
