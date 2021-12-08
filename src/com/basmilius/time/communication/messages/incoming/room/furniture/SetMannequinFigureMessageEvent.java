package com.basmilius.time.communication.messages.incoming.room.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.furniture.MannequinUserItem;
import com.basmilius.time.habbohotel.utils.avatar.LookFunctions;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetMannequinFigure)
public class SetMannequinFigureMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int furniId = packet.readInt();
		final MannequinUserItem mannequin = Bootstrap.getEngine().getGame().getItemsManager().getUserItemWithType(furniId, MannequinUserItem.class);

		if (mannequin == null)
			return;

		final String gender = connection.getHabbo().getGender().toLowerCase();
		final String figure = LookFunctions.getFigureString(connection.getHabbo().getLook(), "hr,hd,ea,ha,he,fa");
		String outfitName = "";

		if (mannequin.getExtraData().contains(";"))
		{
			String[] data = mannequin.getExtraData().split(";");
			outfitName = data[2];
		}

		mannequin.setExtraData(gender + ";" + figure + ";" + outfitName);
		mannequin.updateStateInRoom();
		mannequin.updateAllDataInRoom();
	}

}
