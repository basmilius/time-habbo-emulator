package com.basmilius.time.communication.messages.incoming.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbohelpers.HabboHelper;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersToolComposer;

@Event(id = Incoming.HabboHelpersTool)
public class HabboHelpersToolEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final boolean onDuty = packet.readBoolean();
		final boolean handleTourRequests = packet.readBoolean();
		final boolean handleHelpRequests = packet.readBoolean();
		final boolean handleChatReviews = packet.readBoolean();

		final HabboHelper helper = Bootstrap.getEngine().getGame().getHabboHelpersManager().createOrGetHelper(connection.getHabbo());
		helper.setHelperStatus(onDuty, handleChatReviews, handleHelpRequests, handleTourRequests);

		final int guides = Bootstrap.getEngine().getGame().getHabboHelpersManager().getCountGuides();
		final int guardians = Bootstrap.getEngine().getGame().getHabboHelpersManager().getCountGuardians();
		final int helpers = Bootstrap.getEngine().getGame().getHabboHelpersManager().getCountHelpers();

		connection.send(new HabboHelpersToolComposer(onDuty, guides, helpers, guardians));
	}

}
