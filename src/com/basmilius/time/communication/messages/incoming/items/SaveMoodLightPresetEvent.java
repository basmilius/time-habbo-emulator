package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SaveMoodLightPreset)
public class SaveMoodLightPresetEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		/*if (!client.getHabbo().isInRoom())
			return;

		int presetId = packet.readInt();
		packet.readInt();
		String color = packet.readString();
		int opacity = packet.readInt();

		UserItem moodLight = client.getHabbo().getCurrentRoom().getWallItemWithInteraction(InteractionType.MoodLight);

		if (moodLight == null)
			return;

		String[] data = new String[5];
		data[0] = "2";
		data[1] = Integer.toString(presetId);
		data[2] = "2";
		data[3] = color;
		data[4] = Integer.toString(opacity);

		String newData = data[0];

		for (int i = 1; i < data.length; i++)
		{
			newData += "," + data[i];
		}

		moodLight.setExtraData(newData);
		moodLight.updateAllDataInRoom();*/
	}

}
