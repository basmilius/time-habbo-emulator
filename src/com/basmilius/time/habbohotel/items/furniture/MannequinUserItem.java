package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.habbohotel.utils.avatar.LookFunctions;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class MannequinUserItem extends UserItem
{

	public MannequinUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public MannequinUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredUserItem)
	{

	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		if (unit.getUnitType() == RoomUnitType.USER)
		{
			if (!this.getExtraData().contains(";"))
				return;

			RoomUser user = (RoomUser) unit;

			String[] data = this.getExtraData().split(";");
			String newLook = LookFunctions.getFigureStringAllow(user.getHabbo().getLook(), "hr,hd,ea,ha,he,fa") + "." + data[1];

			user.getHabbo().setLook(newLook, user.getHabbo().getGender(), user.getHabbo().getMotto());
		}
	}

	@Override
	public boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{

	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{

	}

	@Override
	public void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(0);

		response.appendInt(1);
		response.appendInt(3);

		if (this.getExtraData().contains(";"))
		{
			String[] data = this.getExtraData().split(";");
			response.appendString("GENDER");
			response.appendString(data[0]);
			response.appendString("FIGURE");
			response.appendString(data[1]);
			response.appendString("OUTFIT_NAME");
			response.appendString(data[2]);
		}
		else
		{
			response.appendString("GENDER");
			response.appendString(this.getHabbo().getGender());
			response.appendString("FIGURE");
			response.appendString(LookFunctions.getFigureString(this.getHabbo().getLook(), "hr,hd,ea,ha,he,fa"));
			response.appendString("OUTFIT_NAME");
			response.appendString(this.getHabbo().getUsername() + "'s look");
		}
	}

}
