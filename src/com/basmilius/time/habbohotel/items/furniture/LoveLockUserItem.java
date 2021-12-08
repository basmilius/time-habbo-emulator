package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.items.LoveLockConfirmComposer;
import com.basmilius.time.util.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@SuppressWarnings("unused")
public class LoveLockUserItem extends UserItem
{

	public LoveLockUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public LoveLockUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	public Habbo getUserOne()
	{
		if (this.getServerData().has("love_lock") && this.getServerData().getJSONObject("love_lock").has("user_one"))
			return Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.getServerData().getJSONObject("love_lock").getInt("user_one"));
		return null;
	}

	public int getUserOneConfirmed()
	{
		return this.getServerData().has("love_lock") && this.getServerData().getJSONObject("love_lock").has("user_one_confirmed") ? this.getServerData().getJSONObject("love_lock").getInt("user_one_confirmed") : -1;
	}

	public Habbo getUserTwo()
	{
		if (this.getServerData().has("love_lock") && this.getServerData().getJSONObject("love_lock").has("user_two"))
			return Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.getServerData().getJSONObject("love_lock").getInt("user_two"));
		return null;
	}

	public int getUserTwoConfirmed()
	{
		return this.getServerData().has("love_lock") && this.getServerData().getJSONObject("love_lock").has("user_two_confirmed") ? this.getServerData().getJSONObject("love_lock").getInt("user_two_confirmed") : -1;
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
		if (unit.getUnitType() == RoomUnitType.USER && this.getRoom().getRoomData().getPermissions().isOwner(((RoomUser) unit).getHabbo()))
		{
			if (this.getExtraData().equals("1"))
				return;

			final Node unitOneNode;
			final Node unitTwoNode;

			switch (this.getRot())
			{
				case 2:
					unitOneNode = new Node(this.getX(), this.getY() + 1);
					unitTwoNode = new Node(this.getX(), this.getY() - 1);
					break;
				case 4:
					unitOneNode = new Node(this.getX() + 1, this.getY());
					unitTwoNode = new Node(this.getX() - 1, this.getY());
					break;
				default:
					return;
			}

			final RoomUnit unitOnNodeOne = this.getRoom().getRoomUnitsHandler().getUnitAt(unitOneNode);
			final RoomUnit unitOnNodeTwo = this.getRoom().getRoomUnitsHandler().getUnitAt(unitTwoNode);

			if (unitOnNodeOne == null || unitOnNodeTwo == null)
			{
				((RoomUser) unit).getHabbo().getConnection().sendNotif(GenericAlertType.TEXT, Bootstrap.getEngine().getConfig().getString("str.furniture.lock.user.missing", "You and your friend must stand side by side to the lock!"));
				return;
			}

			if (unitOnNodeOne.getUnitType() != RoomUnitType.USER && unitOnNodeTwo.getUnitType() != RoomUnitType.USER)
			{
				((RoomUser) unit).getHabbo().getConnection().sendNotif(GenericAlertType.TEXT, Bootstrap.getEngine().getConfig().getString("str.furniture.lock.user.cant", "You can't lock your friendship to that partner! Please choose another one."));
				return;
			}

			unitOnNodeOne.setCanWalk(false);
			unitOnNodeTwo.setCanWalk(false);

			this.getServerData().put("love_lock", new JSONObject());
			this.getServerData().getJSONObject("love_lock").put("user_one", unitOnNodeOne.getId());
			this.getServerData().getJSONObject("love_lock").put("user_one_confirmed", -1);
			this.getServerData().getJSONObject("love_lock").put("user_two", unitOnNodeTwo.getId());
			this.getServerData().getJSONObject("love_lock").put("user_two_confirmed", -1);
			this.saveServerData();

			((RoomUser) unitOnNodeOne).getConnection().send(new LoveLockConfirmComposer(this.getId(), true));
			((RoomUser) unitOnNodeTwo).getConnection().send(new LoveLockConfirmComposer(this.getId(), true));
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
			response.appendInt(1);

		response.appendInt(2);

		if (this.getServerData().has("love_lock") && this.getUserOne() != null && this.getUserTwo() != null && this.getServerData().getJSONObject("love_lock").has("locked_on"))
		{
			response.appendInt(6);
			response.appendString(this.getExtraData());
			response.appendString(this.getUserOne().getUsername());
			response.appendString(this.getUserTwo().getUsername());
			response.appendString(this.getUserOne().getLook());
			response.appendString(this.getUserTwo().getLook());
			response.appendString(new SimpleDateFormat("dd-MM-yyyy").format(((long) this.getServerData().getJSONObject("love_lock").getInt("locked_on") * (long) 1000)));
		}
		else
		{
			response.appendInt(1);
			response.appendString("0");
		}
	}

}
