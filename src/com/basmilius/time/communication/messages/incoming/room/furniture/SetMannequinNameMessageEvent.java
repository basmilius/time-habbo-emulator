package com.basmilius.time.communication.messages.incoming.room.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.furniture.MannequinUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetMannequinName)
public class SetMannequinNameMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int furniId = packet.readInt();
		final MannequinUserItem mannequin = Bootstrap.getEngine().getGame().getItemsManager().getUserItemWithType(furniId, MannequinUserItem.class);

		if (mannequin == null)
			return;

		String gender = "";
		String figure = "";
		final String outfitName = packet.readString();

		if (mannequin.getExtraData().contains(";"))
		{
			String[] data = mannequin.getExtraData().split(";");
			gender = data[0];
			figure = data[1];
		}

		mannequin.setExtraData(gender + ";" + figure + ";" + outfitName);
		mannequin.updateStateInRoom();
	}

}
