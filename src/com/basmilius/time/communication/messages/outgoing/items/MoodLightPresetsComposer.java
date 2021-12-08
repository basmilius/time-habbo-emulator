package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class MoodLightPresetsComposer extends MessageComposer
{

	private final UserItem moodLight;
	private final int currentPreset;
	private final int moodLightState;

	public MoodLightPresetsComposer(UserItem moodLight)
	{
		this.moodLight = moodLight;
		this.moodLight.getHabbo().loadSettings();

		String[] data = this.moodLight.getExtraData().split(" ");
		if (data.length > 1)
		{
			this.currentPreset = Integer.parseInt(data[1]);
			this.moodLightState = Integer.parseInt(data[0]);
		}
		else
		{
			this.currentPreset = 1;
			this.moodLightState = 1;
		}
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.MoodlightPresets);
		response.appendInt(3);
		response.appendInt(this.currentPreset);
		for (int i = 1; i <= 3; i++)
		{
			if (this.moodLight.getHabbo().getSettings().getMoodLightPresets().split(";").length >= i)
			{
				String[] data = this.moodLight.getHabbo().getSettings().getMoodLightPresets().split(";")[(i - 1)].split(",");
				response.appendInt(Integer.parseInt(data[0]));
				response.appendInt(2);
				response.appendString(data[1]);
				response.appendInt(Integer.parseInt(data[2]));
			}
			else
			{
				String[] data = this.moodLight.getHabbo().getSettings().getMoodLightPresets().split(";")[(i - 1)].split(",");
				response.appendInt(i);
				response.appendInt(2);
				response.appendString("#000000");
				response.appendInt(75);
			}
		}
		return response;
	}

}
