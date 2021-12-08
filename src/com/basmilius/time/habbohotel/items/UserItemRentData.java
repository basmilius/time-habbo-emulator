package com.basmilius.time.habbohotel.items;

import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.util.TimeUtils;
import com.basmilius.time.util.json.JSONObject;

public class UserItemRentData
{

	private final UserItem item;

	public UserItemRentData(final UserItem item)
	{
		this.item = item;

		if (!this.item.getServerData().has("rentable") && this.isRentable())
		{
			this.item.getServerData().put("rentable", new JSONObject());
			this.item.getServerData().getJSONObject("rentable").put("duration", 3600);
			this.item.getServerData().getJSONObject("rentable").put("expiration", 0);
			this.item.getServerData().getJSONObject("rentable").put("started", false);
			this.item.saveServerData();
		}
	}

	public final boolean isRentable()
	{
		return this.item.getItemBoughtType() == BoughtType.RENTABLE;
	}

	public final boolean isRentPeriodStarted()
	{
		if (this.isRentable() && this.item.getServerData().has("rentable"))
		{
			final JSONObject rentable = this.item.getServerData().getJSONObject("rentable");
			return rentable.has("started") && rentable.getBoolean("started");
		}
		return false;
	}

	public final int getDurationPeriod()
	{
		if (this.isRentable() && this.item.getServerData().has("rentable"))
		{
			final JSONObject rentable = this.item.getServerData().getJSONObject("rentable");
			return rentable.has("duration") ? rentable.getInt("duration") : 3600;
		}
		return 0;
	}

	public final int getExipiration()
	{
		if (this.isRentable() && this.item.getServerData().has("rentable"))
		{
			final JSONObject rentable = this.item.getServerData().getJSONObject("rentable");
			return rentable.has("expiration") ? rentable.getInt("expiration") : -1;
		}
		return -1;
	}

	public final int getExipirationSeconds()
	{
		if (!this.isRentable())
			return -1;

		if (!this.isRentPeriodStarted())
			return this.getDurationPeriod();

		return (this.getExipiration() - TimeUtils.getUnixTimestamp());
	}

	public final UserItem getItem()
	{
		return this.item;
	}

}
