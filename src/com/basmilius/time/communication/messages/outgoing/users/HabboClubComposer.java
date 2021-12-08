package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.util.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class HabboClubComposer extends MessageComposer
{

	private final boolean fromCatalogue;
	private final Habbo habbo;

	public HabboClubComposer(Habbo habbo, boolean fromCatalogue)
	{
		this.fromCatalogue = fromCatalogue;
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		Map<String, Integer> clubData = new HashMap<>();
		clubData.put("activated", 0);
		clubData.put("expiration", 0);
		if (this.habbo.getSubscriptions().hasSubscription("habbo_club"))
		{
			clubData = this.habbo.getSubscriptions().getSubscription("habbo_club");
		}

		response.init(Outgoing.HabboClub);
		if (this.habbo.getSubscriptions().hasSubscription("habbo_club"))
		{
			final int timestamp = TimeUtils.getUnixTimestamp();
			final int diff[] = new int[]{0, 0, 0, 0, 0};
			int diffInSeconds = (clubData.get("expiration") - timestamp);

			diff[4] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
			diff[3] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
			diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
			diff[1] = (diffInSeconds = (diffInSeconds / 24));
			diff[0] = (diffInSeconds = (diffInSeconds / 30)) >= 12 ? diffInSeconds % 12 : diffInSeconds;
			diff[0] = (int) Math.floor(diff[0] / 31.0);

			response.appendString("club_habbo");
			response.appendInt((diff[1] - (31 * diff[0])));
			response.appendInt(1);
			response.appendInt(diff[0]);
			response.appendInt(1);
			response.appendBoolean(true);
			response.appendBoolean(fromCatalogue);
			response.appendInt(0);
			response.appendInt(0);
			response.appendInt(clubData.get("expiration") - clubData.get("activated"));
		}
		else
		{
			response.appendString("club_habbo");
			response.appendInt(0);
			response.appendInt(0);
			response.appendInt(0);
			response.appendInt(0);
			response.appendBoolean(false);
			response.appendBoolean(fromCatalogue);
			response.appendInt(0);
			response.appendInt(0);
			response.appendInt(0);
		}
		return response;
	}

}
