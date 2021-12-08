package com.basmilius.time.habbohotel.catalogue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.ObjectUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogueItem extends ISave implements ISerialize
{

	private int id;
	private int pageId;
	private String itemIds;
	private String catalogueName;
	private int costCredits;
	private int costCurrency;
	private int currencyId;
	private int amount;
	private Map<Integer, Integer> amountBundle;
	private int limitedStack;
	private int limitedSells;
	private String extraData;
	private String badge;
	private String subscriptionType;
	private int subscriptionDays;
	private boolean clubOnly;
	private boolean haveOffer;
	private boolean isLimited;
	private BoughtType itemBoughtType;
	private int itemExpire;
	private int songId;

	public CatalogueItem(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.pageId = result.getInt("page_id");
		this.itemIds = result.getString("item_ids");
		this.catalogueName = result.getString("catalog_name");
		this.costCredits = result.getInt("cost_credits");
		this.costCurrency = result.getInt("cost_currency");
		this.currencyId = result.getInt("currency_id");
		this.amount = result.getInt("amount");
		this.amountBundle = new HashMap<>();
		this.limitedStack = result.getInt("limited_stack");
		this.limitedSells = result.getInt("limited_sells");
		this.extraData = result.getString("extradata");
		this.badge = result.getString("badge");
		this.subscriptionType = result.getString("subscription_type");
		this.subscriptionDays = result.getInt("subscription_days");
		this.clubOnly = (result.getInt("club_only") == 1);
		this.haveOffer = (result.getString("have_offer").equals("1"));
		this.isLimited = (this.limitedStack > 0);
		this.itemBoughtType = Bootstrap.getEngine().getGame().getItemsManager().getBoughtTypeByString(result.getString("item_bought_type"));
		this.itemExpire = result.getInt("item_expire");
		this.songId = result.getInt("song_id");

		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.catalog.items", 30000));
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.catalogueName;
	}

	public String getExtraData()
	{
		return this.extraData;
	}

	public int getCostsCredits()
	{
		return this.costCredits;
	}

	public int getCostsCurrency()
	{
		return this.costCurrency;
	}

	public int getCurrencyId()
	{
		return this.currencyId;
	}

	public int getAmount()
	{
		return this.amount;
	}

	public boolean getIsClubOnly()
	{
		return this.clubOnly;
	}

	public String getSubscriptionType()
	{
		return this.subscriptionType;
	}

	public int getSubscriptionDays()
	{
		return this.subscriptionDays;
	}

	public BoughtType getBoughtType()
	{
		return this.itemBoughtType;
	}

	public int getItemExpire()
	{
		return this.itemExpire;
	}

	public int getSongId()
	{
		return this.songId;
	}

	public int getPageId()
	{
		return this.pageId;
	}

	public List<Item> getBaseItems()
	{
		List<Item> items = new ArrayList<>();
		String[] itemIds = this.itemIds.split(",");

		for (String itemId : itemIds)
		{
			if (itemId.contains(":"))
			{
				String[] d = itemId.split(":");
				if (d.length > 1 && !d[1].isEmpty() && ObjectUtils.isNumeric(d[0]) && ObjectUtils.isNumeric(d[1]))
				{
					this.amountBundle.put(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
				}
				itemId = d[0];
			}
			else if (!itemId.isEmpty())
			{
				this.amountBundle.put(Integer.parseInt(itemId), 1);
			}

			if (!ObjectUtils.isNumeric(itemId))
				continue;

			final int itemIdInt = Integer.parseInt(itemId);
			final Item item = Bootstrap.getEngine().getGame().getItemsManager().getItem(itemIdInt);

			if (item == null)
				continue;

			items.add(item);
		}

		return items;
	}

	public Map<Integer, Integer> getBundleAmount()
	{
		return this.amountBundle;
	}

	public void limitedBought()
	{
		if (!this.isLimited)
			return;
		this.limitedSells++;

		this.setNeedsUpdate(true);
	}

	public boolean isSoldOut()
	{
		return this.isLimited && this.limitedSells >= this.limitedStack;
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendInt(this.id);
		response.appendString(((this.catalogueName.startsWith("window_")) ? "DEV " : "") + this.catalogueName);
		response.appendBoolean(this.itemBoughtType == BoughtType.RENTABLE);
		response.appendInt(this.costCredits);
		response.appendInt(this.costCurrency);
		response.appendInt(this.currencyId);
		response.appendBoolean(this.getBaseItems().size() > 0 && this.getBaseItems().get(0).getAllowAsGift());

		this.serializeData(response);

		response.appendInt((this.clubOnly) ? 1 : 0);
		response.appendBoolean(this.haveOffer);
	}

	@SuppressWarnings("ConstantConditions")
	public void serializeData(final ServerMessage response)
	{
		if (this.badge.equals("") || this.badge.isEmpty())
		{
			response.appendInt(this.getBaseItems().size());
		}
		else
		{
			response.appendInt(this.getBaseItems().size() + 1);
			response.appendString("b");
			response.appendString(this.badge);
		}
		for (final Item item : this.getBaseItems())
		{
			response.appendString(item.getType());
			if (item.getType().equals("b"))
			{
				response.appendString("RADZZ");
			}
			else
			{
				response.appendInt(item.getSpriteId());
				if (this.catalogueName.contains("wallpaper_single") || this.catalogueName.contains("floor_single") || this.catalogueName.contains("landscape_single"))
				{
					String[] paperType = this.catalogueName.split("_");
					response.appendString(paperType[2]);
				}
				else if (Bootstrap.getEngine().getGame().getCatalogueManager().getPage(this.pageId).getPageLayout().equals("bots"))
				{
					response.appendString(this.extraData);
				}
				else if (this.getSongId() > 0)
				{
					try
					{
						response.appendString(StringCrypt.getSHA1Hash(Integer.toString(this.getSongId())));
					}
					catch (NoSuchAlgorithmException e)
					{
						response.appendString("");
					}
				}
				else
				{
					response.appendString("");
				}
				response.appendInt(this.amountBundle.get(item.getId()));
			}

			// TODO: Limited items. boolean isLimited, int max sells, int
			// current sells
			response.appendBoolean(this.isLimited);
			if (this.isLimited)
			{
				response.appendInt(this.limitedStack);
				response.appendInt(this.limitedStack - this.limitedSells);
			}
		}
	}

	@Override
	public void save() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE catalog_items SET limited_sells = ? WHERE id = ?");
		{
			if (statement != null)
			{
				statement.setInt(1, this.limitedSells);
				statement.setInt(2, this.id);
				statement.execute();
			}
		}

		this.setNeedsUpdate(false);
	}

}
