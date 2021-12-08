package com.basmilius.time.habbohotel.quests;

public enum QuestType
{
	ExloreFindFurniture,
	FurnitureDecorationFloor,
	FurnitureDecorationWall,
	FurnitureMove,
	FurniturePick,
	FurniturePlace,
	FurnitureRotate,
	FurnitureStack,
	FurnitureSwitch,
	ProfileBadge,
	ProfileChangeLook,
	ProfileChanteMotto,
	SocialChat,
	SocialDance,
	SocialFriend,
	SocialRespect,
	SocialVisit,
	SocialWave,
	SummerEnterRoom,
	
	Add25Friends,
	Wave10Users;
	
	public final String asString()
	{
		switch (this)
		{
			case FurnitureDecorationFloor:
				return "PLACE_FLOOR";
			case FurnitureDecorationWall:
				return "PLACE_WALLPAPER";
			case FurnitureMove:
				return "MOVE_ITEM";
			case FurniturePick:
				return "PICKUP_ITEM";
			case FurniturePlace:
				return "PLACE_ITEM";
			case FurnitureRotate:
				return "ROTATE_ITEM";
			case FurnitureStack:
				return "STACK_ITEM";
			case FurnitureSwitch:
				return "SWITCH_ITEM_STATE";
			case ProfileBadge:
				return "WEAR_BADGE";
			case ProfileChangeLook:
				return "CHANGE_FIGURE";
			case ProfileChanteMotto:
				return "CHANGE_MOTTO";
			default:
				return "FIND_STUFF";
		}
	}
}
