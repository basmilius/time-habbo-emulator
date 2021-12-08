package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.RoomBgChange)
public class RoomBgChangeEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final int hue = packet.readInt();
		final int saturation = packet.readInt();
		final int brightness = packet.readInt();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null)
			return;

		if (!(connection.getHabbo().isInRoom() && item.getRoom().getRoomData().getPermissions().hasRights(connection.getHabbo())))
			return;

		final String[] data = item.getExtraData().split(";");

		if (data.length == 4)
		{
			item.setExtraData(data[0] + ";" + hue + ";" + saturation + ";" + brightness + "");
		}
		else
		{
			item.setExtraData("1;" + hue + ";" + saturation + ";" + brightness + "");
		}

		item.updateAllDataInRoom();
	}

}
