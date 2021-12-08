package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.habbohotel.items.furniture.MoodLightUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

import java.util.List;

@Event(id = Incoming.ToggleMoodLightState)
public class ToggleMoodLightStateEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		final List<MoodLightUserItem> moodLights = connection.getHabbo().getCurrentRoom().getRoomObjectsHandler().getItems(MoodLightUserItem.class);
		
		if (moodLights.size() != 1)
			return;
		
		final MoodLightUserItem moodLight = moodLights.get(0);

		if (moodLight == null)
			return;

		String[] data = moodLight.getExtraData().split(",");
		if (data.length > 1)
		{
			data[0] = (data[0].equalsIgnoreCase("1")) ? "2" : "1";
		}
		else
		{
			data = new String[5];
			data[0] = "2";
			data[1] = "1";
			data[2] = "2";
			data[3] = "#000000";
			data[4] = "75";
		}

		String newData = data[0];

		for (int i = 1; i < data.length; i++)
		{
			newData += "," + data[i];
		}

		moodLight.setExtraData(newData);
		moodLight.updateAllDataInRoom();
	}

}