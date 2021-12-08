package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WelcomePresentComposer extends MessageComposer
{

	private String mail;
	private boolean canOpen;
	private boolean canChange;
	private int itemId;
	private boolean showWindow;

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.WelcomePresent);
		response.appendString(this.mail);
		response.appendBoolean(this.canOpen);
		response.appendBoolean(this.canChange);
		response.appendInt(this.itemId);
		response.appendBoolean(this.showWindow);
		return response;
	}

}
