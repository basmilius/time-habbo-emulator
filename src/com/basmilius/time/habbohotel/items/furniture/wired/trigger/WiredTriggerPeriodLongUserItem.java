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
public class WiredTriggerPeriodLongUserItem extends WiredTriggerUserItem
{

	private Timer timer;

	public WiredTriggerPeriodLongUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredTriggerPeriodLongUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
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

		int delay = ((this.getValue("delay", 20) * 5) * 1000);
		if (delay < 5000)
		{
			delay = 5000;
		}

		this.timer = new Timer();
		this.timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				WiredTriggerPeriodLongUserItem.this.toggleState();
				WiredTriggerPeriodLongUserItem.this.handleStack(null, null, null);

				if (WiredTriggerPeriodLongUserItem.this.getRoom().isActive())
					WiredTriggerPeriodLongUserItem.this.reset();
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
		response.appendInt(12);
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
