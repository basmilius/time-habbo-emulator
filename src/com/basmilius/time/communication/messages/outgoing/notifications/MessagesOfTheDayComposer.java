package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class MessagesOfTheDayComposer extends MessageComposer
{

	private final String[] _messages;

	public MessagesOfTheDayComposer(List<String> messages)
	{
		this._messages = new String[messages.size()];

		for (String message : messages)
		{
			this._messages[messages.indexOf(message)] = message;
		}
	}

	public MessagesOfTheDayComposer(String[] _messages)
	{
		this._messages = _messages;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.MessagesOfTheDay);
		response.appendInt(this._messages.length);
		for (String message : this._messages)
		{
			response.appendString(message);
		}
		return response;
	}

}
