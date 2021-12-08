package com.basmilius.time.habbohotel.items;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public final class Item
{

	private final int id;
	private String publicName;
	private String itemName;
	private String type;
	private int width;
	private int length;
	private int offerId;
	private int spriteId;
	private double stackHeight;
	private boolean canStack;
	private boolean canSit;
	private boolean canLay;
	private boolean isWalkable;
	private boolean allowRecycle;
	private boolean allowTrade;
	private boolean allowMarketplaceSell;
	private boolean allowAsGift;
	private boolean allowInventoryStack;
	private String interactionType;
	private int interactionModesCount;
	private int[] vendingIds;
	private int effectIdMale;
	private int effectIdFemale;
	private double[] adjustableHeights;

	public Item(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.update(result);
	}

	public void update(final ResultSet result) throws SQLException
	{
		this.publicName = result.getString("public_name");
		this.itemName = result.getString("item_name");
		this.type = result.getString("type");
		this.width = result.getInt("width");
		this.length = result.getInt("length");
		this.offerId = result.getInt("offer_id");
		this.spriteId = result.getInt("sprite_id");
		this.stackHeight = result.getDouble("stack_height");
		this.canStack = (result.getInt("allow_stack") == 1);
		this.canSit = (result.getInt("allow_sit") == 1);
		this.canLay = (result.getInt("allow_lay") == 1);
		this.isWalkable = (result.getInt("allow_walk") == 1);
		this.allowRecycle = (result.getInt("allow_recycle") == 1);
		this.allowTrade = (result.getInt("allow_trade") == 1);
		this.allowMarketplaceSell = (result.getInt("allow_marketplace_sell") == 1);
		this.allowAsGift = (result.getInt("allow_gift") == 1);
		this.allowInventoryStack = (result.getInt("allow_inventory_stack") == 1);
		this.vendingIds = Arrays.stream(result.getString("vending_ids").replace(" ", "").split(",")).filter(vendingId -> !vendingId.isEmpty()).mapToInt(Integer::parseInt).toArray();
		this.effectIdMale = result.getInt("effect_id_male");
		this.effectIdFemale = result.getInt("effect_id_female");

		this.interactionType = result.getString("interaction_type");
		this.interactionModesCount = result.getInt("interaction_modes_count");

		this.adjustableHeights = Arrays.stream(result.getString("adjustable_heights").replace(" ", "").split(",")).filter(adjustableHeight -> !adjustableHeight.isEmpty()).mapToDouble(Double::parseDouble).toArray();
	}

	public int getId()
	{
		return this.id;
	}

	public int getOfferId()
	{
		return this.offerId;
	}

	public String getPublicName()
	{
		return this.publicName;
	}

	public String getItemName()
	{
		return this.itemName;
	}

	public String getType()
	{
		return this.type;
	}

	public int getBaseWidth()
	{
		return this.width;
	}

	public int getBaseLength()
	{
		return this.length;
	}

	public int getSpriteId()
	{
		return this.spriteId;
	}

	public double getStackHeight()
	{
		return this.stackHeight;
	}

	public boolean getBaseCanStack()
	{
		return this.canStack;
	}

	public boolean getBaseCanSit()
	{
		return this.canSit;
	}

	public boolean getBaseCanLay()
	{
		return this.canLay;
	}

	public boolean getIsWalkable()
	{
		return this.isWalkable;
	}

	public boolean getAllowAsGift()
	{
		return this.allowAsGift;
	}

	public boolean getAllowRecycle()
	{
		return this.allowRecycle;
	}

	public boolean getAllowInventoryStack()
	{
		return this.allowInventoryStack;
	}

	public boolean getAllowMarkedplaceSell()
	{
		return this.allowMarketplaceSell;
	}

	public boolean getAllowTrade()
	{
		return this.allowTrade;
	}

	public int getEffectMale()
	{
		return this.effectIdMale;
	}

	public int getEffectFemale()
	{
		return this.effectIdFemale;
	}

	public String getInteractionType()
	{
		return this.getInteractionType(false);
	}

	public String getInteractionType(boolean merged)
	{
		if (merged)
		{
			if (this.interactionType.startsWith("battlebanzai_"))
				return InteractionType.BattleBanzai;
			else if (this.interactionType.startsWith("freeze_"))
				return InteractionType.Freeze;
		}
		return this.interactionType;
	}

	public int getInteractionModesCount()
	{
		return this.interactionModesCount;
	}

	public int[] getVendingIds()
	{
		return this.vendingIds;
	}

	public double getAdjustableHeight(int id)
	{
		if (id >= this.adjustableHeights.length)
			return 0.0;

		return this.adjustableHeights[id];
	}

	public boolean hasAdjustableHeights()
	{
		return (this.adjustableHeights.length > 0);
	}
	
}
