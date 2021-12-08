package com.basmilius.time.habbohotel.habbo.messenger;

import com.basmilius.time.habbohotel.habbo.Habbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessengerCache
{

	private final List<Map<Habbo, Map<String, Integer>>> _offlineMessages;

	public MessengerCache(MessengerManager manager)
	{
		this._offlineMessages = new ArrayList<>();
	}

	public List<Map<Habbo, Map<String, Integer>>> getOfflineMessages()
	{
		return this._offlineMessages;
	}

	public void addOfflineMessage(Habbo habbo, final String message, final Integer time)
	{
		Map<Habbo, Map<String, Integer>> msgData = new HashMap<>();
		msgData.put(habbo, new HashMap<String, Integer>()
		{
			private static final long serialVersionUID = 1L;

			{
				put(message, time);
			}
		});
		this._offlineMessages.add(msgData);
	}

	public void clearOfflineMessages()
	{
		this._offlineMessages.clear();
	}

}
