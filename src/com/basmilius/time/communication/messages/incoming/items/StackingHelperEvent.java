package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.StackingHelper)
public class StackingHelperEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final double stackHeight = Double.parseDouble(Integer.toString(packet.readInt()));

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null)
			return; // Stacking helper doesn't exists

		double z = (stackHeight / 100.0);

		if (stackHeight == -100)
		{
			z = item.getRoom().getRoomObjectsHandler().getStackHeight(item.getNode(), null);
		}

		if (stackHeight > (20 * 100))
		{
			connection.sendNotif(GenericAlertType.TEXT, Bootstrap.getEngine().getConfig().getString("error.stacking.helper.too_high", "That's too heigh!"));
			return;
		}

		item.setZ(z);
		item.updateAllDataInRoom();
		item.getRoom().getRoomObjectsHandler().updateGameMap();
	}

}
