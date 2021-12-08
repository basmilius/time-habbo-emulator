package com.basmilius.time.habbohotel.items.furniture.wired.effect;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredEffectUserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.json.JSONArray;
import com.basmilius.time.util.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredEffectMatchToShotUserItem extends WiredEffectUserItem
{

	public WiredEffectMatchToShotUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredEffectMatchToShotUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		this.toggleState();

		(new Thread(() -> {
			try
			{
				this.hasWiredServerData();
				
				if (!this.getServerData().getJSONObject("wired").has("presets"))
					return;

				final JSONArray presets = this.getServerData().getJSONObject("wired").getJSONArray("presets");
				
				if (this.getValue("delay", 0) > 0)
				{
					Thread.sleep(((this.getValue("delay", 1) / 2) * 1000));
				}

				for (final UserItem linkedItem : this.getLinkedItems())
				{
					JSONObject preset = null;
					
					for (int i = 0; i < presets.length(); i++)
					{
						if (presets.getJSONObject(i).has("id") && presets.getJSONObject(i).getInt("id") == linkedItem.getId())
						{
							preset = presets.getJSONObject(i);
							break;
						}
					}

					if (preset == null)
						continue;

					final String extraData = preset.getString("data");
					final int r = preset.getInt("r");
					final int x = preset.getInt("x");
					final int y = preset.getInt("y");
					final double z = preset.getDouble("z");

					if (this.getSetting("extra_data", false))
						linkedItem.setExtraData(extraData);

					if (this.getSetting("rotation", false))
						linkedItem.setRot(r);

					linkedItem.updateAllDataInRoom();

					if (this.getSetting("node", false) && this.getRoom().getRoomObjectsHandler().canPlace(new Node(x, y), linkedItem, false))
					{
						linkedItem.rollTo(new Node(x, y), z, null);

						linkedItem.setX(x);
						linkedItem.setY(y);
						linkedItem.setZ(z);
					}
				}

				this.getRoom().getRoomObjectsHandler().updateGameMap();
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		})).start();

		return true;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		this.hasWiredServerData();

		final int[] settings = packet.readIntArray();
		this.setSetting("extra_data", settings.length > 0 && settings[0] == 1);
		this.setSetting("rotation", settings.length > 1 && settings[1] == 1);
		this.setSetting("node", settings.length > 2 && settings[2] == 1);

		packet.readString();
		
		final int[] items = packet.readIntArray();
		this.saveLinkedItems(items);
		if(this.getServerData().getJSONObject("wired").has("presets"))
		{
			this.getServerData().getJSONObject("wired").remove("presets");
		}
		this.getServerData().getJSONObject("wired").put("presets", new JSONArray());
		for (final UserItem item : this.getLinkedItems())
		{
			final JSONObject preset = new JSONObject();
			preset.put("id", item.getId());
			preset.put("data", item.getExtraData());
			preset.put("r", item.getRot());
			preset.put("x", item.getX());
			preset.put("y", item.getY());
			preset.put("z", item.getZ());
			this.getServerData().getJSONObject("wired").getJSONArray("presets").put(preset);
		}

		this.setValue("delay", packet.readInt());
		this.saveServerData();
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(5);
		response.appendInt(this.getLinkedItems().size());
		for (final UserItem linkedItem : this.getLinkedItems())
		{
			response.appendInt(linkedItem.getId());
		}
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendString("");
		response.appendInt(3);
		response.appendInt(this.getSetting("extra_data", false) ? 1 : 0);
		response.appendInt(this.getSetting("rotation", false) ? 1 : 0);
		response.appendInt(this.getSetting("node", false) ? 1 : 0);
		response.appendInt(0);
		response.appendInt(3);
		response.appendInt(this.getValue("delay", 0));
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
