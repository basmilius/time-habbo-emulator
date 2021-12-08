package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.util.json.JSONObject;

@Event(id = Incoming.SaveBranding)
public class SaveBrandingEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		int settingsCount = packet.readInt();
		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null)
			return;

		if (item.getServerData().has("branding"))
		{
			item.getServerData().remove("branding");
		}

		item.getServerData().put("branding", new JSONObject());

		while (settingsCount > 0)
		{
			String key = packet.readString();
			String val = packet.readString();

			item.getServerData().getJSONObject("branding").put(key, val);

			settingsCount = (settingsCount - 2);
		}

		item.saveServerData();
		item.updateStateInRoom();
	}

}
