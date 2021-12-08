package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboLandingExpiringCatalogPageComposer extends MessageComposer
{

	private final String pageName;
	private final int secondsLeft;
	private final String pageImage;

	public HabboLandingExpiringCatalogPageComposer(final String pageName, final int secondsLeft, final String pageImage)
	{
		this.pageName = pageName;
		this.secondsLeft = secondsLeft;
		this.pageImage = pageImage;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		return response.init(Outgoing.HabboLandingExpiringCatalogPage).appendString(this.pageName).appendInt(this.secondsLeft).appendString(this.pageImage);
	}

}
