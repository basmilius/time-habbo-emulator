package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.habbo.HabboWardrobe;
import com.basmilius.time.habbohotel.habbo.HabboWardrobeLook;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WardrobeComposer extends MessageComposer
{

	private final HabboWardrobe wardrobe;

	public WardrobeComposer(HabboWardrobe wardrobe)
	{
		this.wardrobe = wardrobe;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Wardrobe);
		response.appendInt(1);
		response.appendInt(this.wardrobe.getLooks().size());
		for (HabboWardrobeLook look : this.wardrobe.getLooks())
		{
			response.appendInt(look.getSlotId());
			response.appendString(look.getFigure());
			response.appendString(look.getGender());
		}
		return response;
	}

}
