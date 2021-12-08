package com.basmilius.time.habbohotel.catalogue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TargetedOffer
{

	private final int id;
	private final String title;
	private final String description;
	private final String image;
	private final int cost;
	private final int currencyId;
	private final String badge;
	private final int[] itemIds;
	private final String subscriptionType;
	private final int subscriptionTime;
	private final int offerExpire;

	public TargetedOffer(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.title = result.getString("title");
		this.description = result.getString("description");
		this.image = result.getString("image");
		this.cost = result.getInt("cost");
		this.currencyId = result.getInt("currency_id");
		this.badge = result.getString("badge");
		this.itemIds = this.getItemIdsFromResult(result.getString("item_ids"));
		this.subscriptionType = result.getString("subscription_type");
		this.subscriptionTime = result.getInt("subscription_time");
		this.offerExpire = result.getInt("offer_expire");
	}

	private int[] getItemIdsFromResult(final String itemIds)
	{
		final String[] sItems = itemIds.split(",");
		final int[] items = new int[sItems.length];

		for (int i = 0; i < sItems.length; i++)
		{
			items[i] = Integer.parseInt(sItems[i]);
		}

		return items;
	}

	public final int getId()
	{
		return this.id;
	}

	public final String getTitle()
	{
		return this.title;
	}

	public final String getDescription()
	{
		return this.description;
	}

	public final String getImage()
	{
		return this.image;
	}

	public final int getCost()
	{
		return this.cost;
	}

	public final int getCurrencyId()
	{
		return this.currencyId;
	}

	public final String getBadge()
	{
		return this.badge;
	}

	public final int[] getItemIds()
	{
		return this.itemIds;
	}

	public final List<Item> getItems()
	{
		final List<Item> items = new ArrayList<>();

		for (final int itemId : this.itemIds)
		{
			items.add(Bootstrap.getEngine().getGame().getItemsManager().getItem(itemId));
		}

		return items;
	}

	public final String getSubscriptionType()
	{
		return this.subscriptionType;
	}

	public final int getSubscriptionTime()
	{
		return this.subscriptionTime;
	}

	public final int getOfferExpire()
	{
		return this.offerExpire;
	}

}
