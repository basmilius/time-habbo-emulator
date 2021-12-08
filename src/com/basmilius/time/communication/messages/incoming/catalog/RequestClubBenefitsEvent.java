package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.ClubBenefitsComposer;

@Event(id = Incoming.RequestClubBenefits)
public class RequestClubBenefitsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{ // TODO: Club Benefits
		connection.send(new ClubBenefitsComposer(Bootstrap.getEngine().getGame().getCatalogueManager().getPage("club_gift")));
	}

}
