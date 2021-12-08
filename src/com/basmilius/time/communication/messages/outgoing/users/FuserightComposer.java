package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FuserightComposer extends MessageComposer
{

	private final Habbo habbo;

	public FuserightComposer(Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Fuseright);
		response.appendInt(this.habbo.getSubscriptions().hasSubscription("habbo_club") ? 2 : 0);
		if (this.habbo.getUsername().equalsIgnoreCase("bas"))
		{
			response.appendInt(9); // TODO: User perks, SEE CLIENT (:uc)
		}
		else
		{
			response.appendInt(this.habbo.getPermissions().contains("is_staff") ? 8 : this.habbo.getRank());
		}
		response.appendBoolean(this.habbo.getSettings().getIsAmbassador());
		return response;
	}

}
