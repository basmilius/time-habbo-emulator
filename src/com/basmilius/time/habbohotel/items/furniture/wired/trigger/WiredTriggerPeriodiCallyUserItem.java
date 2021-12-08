package com.basmilius.time.habbohotel.items.furniture.wired.trigger;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredTriggerUserItem;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("unused")
public class WiredTriggerPeriodiCallyUserItem extends WiredTriggerUserItem
{

	private Timer timer;

	public WiredTriggerPeriodiCallyUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredTriggerPeriodiCallyUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	public void reset()
	{
		if (this.timer != null)
		{
			try
			{
				this.timer.cancel();
			}
			catch (Exception ignored)
			{
			}
		}

		this.timer = null;

		if (!this.getRoom().isActive())
			return;

		int delay = ((this.getValue("delay", 20) / 2) * 1000);
		if (delay < 500)
		{
			delay = 500;
		}

		this.timer = new Timer();
		this.timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				if (WiredTriggerPeriodiCallyUserItem.this.getRoom() == null)
					return;

				WiredTriggerPeriodiCallyUserItem.this.toggleState();
				WiredTriggerPeriodiCallyUserItem.this.handleStack(null, null, null);

				if (WiredTriggerPeriodiCallyUserItem.this.getRoom().isActive())
					WiredTriggerPeriodiCallyUserItem.this.reset();
			}

		}, delay);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		return true;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		packet.readInt();
		this.setValue("delay", packet.readInt());

		this.reset();
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(5);
		response.appendInt(0);
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendString("");
		response.appendInt(1);
		response.appendInt(this.getValue("delay", 20));
		response.appendInt(0);
		response.appendInt(6);
		response.appendInt(0);
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

}
