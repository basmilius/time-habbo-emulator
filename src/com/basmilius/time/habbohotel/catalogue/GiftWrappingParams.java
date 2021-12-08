package com.basmilius.time.habbohotel.catalogue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.util.json.JSONObject;

/**
 * Params for a Gift.
 */
public class GiftWrappingParams
{

	private final int presentSpriteId;
	private final int boxId;
	private final int ribbonId;
	private final Habbo habboFrom;
	private final Habbo habboTo;
	private final String giftWish;
	private final boolean showHabboFromData;

	private boolean opened;

	/**
	 * Constructor.
	 *
	 * @param data Data from JSON.
	 */
	public GiftWrappingParams (JSONObject data)
	{
		this.presentSpriteId = (int) data.get("presentSpriteId");
		this.boxId = (int) data.get("boxId");
		this.ribbonId = (int) data.get("ribbonId");
		this.habboFrom = Bootstrap.getEngine().getGame().getHabboManager().getHabbo((int) data.get("habboFrom"));
		this.habboTo = Bootstrap.getEngine().getGame().getHabboManager().getHabbo((int) data.get("habboTo"));
		this.giftWish = (String) data.get("giftWish");
		this.showHabboFromData = (boolean) data.get("showHabboFromData");
		this.opened = (boolean) data.get("opened");
	}

	/**
	 * Constructor.
	 *
	 * @param presentSpriteId   Presents Sprite ID.
	 * @param boxId             Presents Box ID.
	 * @param ribbonId          Presents Ribbon ID.
	 * @param habboFrom         Habbo who sended this present.
	 * @param habboTo           Habbo who got this present.
	 * @param giftWish          The gifts wish.
	 * @param showHabboFromData Show the senders data.
	 */
	public GiftWrappingParams (int presentSpriteId, int boxId, int ribbonId, Habbo habboFrom, Habbo habboTo, String giftWish, boolean showHabboFromData)
	{
		this.presentSpriteId = presentSpriteId;
		this.boxId = boxId;
		this.ribbonId = ribbonId;
		this.habboFrom = habboFrom;
		this.habboTo = habboTo;
		this.giftWish = giftWish;
		this.showHabboFromData = showHabboFromData;
		this.opened = false;
	}

	/**
	 * Gets the presents sprite id.
	 *
	 * @return int
	 */
	public int getPresentSpriteId ()
	{
		return this.presentSpriteId;
	}

	/**
	 * Gets the presents box id.
	 *
	 * @return int
	 */
	public int getBoxId ()
	{
		return this.boxId;
	}

	/**
	 * Gets the presents ribbon id.
	 *
	 * @return int
	 */
	public int getRibbonId ()
	{
		return this.ribbonId;
	}

	/**
	 * Gets the Habbo who sended this present.
	 *
	 * @return Habbo
	 */
	public Habbo getHabboFrom ()
	{
		return this.habboFrom;
	}

	/**
	 * Gets the Habbo who got this present.
	 *
	 * @return Habbo
	 */
	public Habbo getHabboTo ()
	{
		return this.habboTo;
	}

	/**
	 * Gets the gift wish.
	 *
	 * @return String
	 */
	public String getGiftWish ()
	{
		return this.giftWish;
	}

	/**
	 * Gets if the senders data is shown.
	 *
	 * @return boolean
	 */
	public boolean getHabboFromData ()
	{
		return this.showHabboFromData;
	}

	/**
	 * Gets if this present is opened.
	 *
	 * @return boolean
	 */
	public boolean isOpened ()
	{
		return this.opened;
	}

	/**
	 * Sets if this present is opened.
	 *
	 * @param opened Opened?
	 */
	public void setOpened (boolean opened)
	{
		this.opened = opened;
	}

	/**
	 * Gets the JSON data for this object.
	 *
	 * @return JSONObject
	 */
	public JSONObject getJsonData ()
	{
		JSONObject data = new JSONObject();

		data.put("presentSpriteId", this.presentSpriteId);
		data.put("boxId", this.boxId);
		data.put("ribbonId", this.ribbonId);
		data.put("habboFrom", this.habboFrom.getId());
		data.put("habboTo", this.habboTo.getId());
		data.put("giftWish", this.giftWish);
		data.put("showHabboFromData", this.showHabboFromData);
		data.put("opened", this.opened);

		return data;
	}

}