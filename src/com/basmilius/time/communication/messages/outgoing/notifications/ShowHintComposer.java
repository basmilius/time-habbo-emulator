package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ShowHintComposer extends MessageComposer
{

	public static final String CREDIT_COUNT = "credit_count";
	public static final String TOOLBAR_ICON_CATALOGUE = "CATALOGUE";
	public static final String TOOLBAR_ICON_MEMENU = "MEMENU";
	public static final String TOOLBAR_ICON_INVENTORY = "INVENTORY";
	public static final String TOOLBAR_ICON_NAVIGATOR = "NAVIGATOR";

	private final String hint;

	public ShowHintComposer(final String hint)
	{
		this.hint = hint;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ShowHint);
		response.appendString(this.hint);
		return response;
	}

}
