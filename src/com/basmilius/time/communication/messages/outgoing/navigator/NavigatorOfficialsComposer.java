package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.navigator.OfficialItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class NavigatorOfficialsComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		final List<OfficialItem> items = Bootstrap.getEngine().getGame().getNavigatorManager().getOfficials(-1);

		response.init(Outgoing.NavigatorOfficialList);
		response.appendInt(Bootstrap.getEngine().getGame().getNavigatorManager().getOfficials().size());
		for (OfficialItem official : items)
		{
			official.serialize(response);
		}
		response.appendInt(0); // Recommended item
		response.appendInt(0);
		return response;
	}

}
