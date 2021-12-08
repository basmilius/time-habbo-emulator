package com.basmilius.time.communication.messages.outgoing.namechange;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class NameChangeClaimResultComposer extends MessageComposer
{

	public static final int CHANGED = 0;
	public static final int TOO_SHORT = 2;
	public static final int TOO_LONG = 3;
	public static final int INVALID = 4;
	public static final int TAKEN = 5;
	public static final int CHANGE_NOT_ALLOWED = 6;

	private int result;
	private String name;
	private List<String> availableNames;

	public NameChangeClaimResultComposer(int result, String name, List<String> availableNames)
	{
		this.result = result;
		this.name = name;
		this.availableNames = availableNames;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NameChangeClaimResult);
		response.appendInt(this.result);
		response.appendString(this.name);
		response.appendInt(this.availableNames.size());
		this.availableNames.forEach(response::appendString);
		return response;
	}

}
