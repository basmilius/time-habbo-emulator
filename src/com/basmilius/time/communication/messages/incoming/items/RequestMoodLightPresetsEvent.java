package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.habbohotel.items.furniture.MoodLightUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.items.MoodLightPresetsComposer;

import java.util.List;

@Event(id = Incoming.RequestMoodLightPresets)
public class RequestMoodLightPresetsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		List<MoodLightUserItem> moodLights = connection.getHabbo().getCurrentRoom().getRoomObjectsHandler().getItems(MoodLightUserItem.class);
		
		if (moodLights.size() != 1)
			return;

		MoodLightUserItem moodLight = moodLights.get(0);

		if (moodLight == null)
			return;

		connection.send(new MoodLightPresetsComposer(moodLight));
	}

}
