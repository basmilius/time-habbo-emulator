package com.basmilius.time.communication.messages.incoming.general;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ClientUserInfo)
public class ClientUserInfoEvent extends MessageEvent
{

	@SuppressWarnings("unused")
	@Override
	public void handle() throws Exception
	{
		int _memoryUsage = packet.readInt();
		String _userAgent = packet.readString();
		String _osVersion = packet.readString();
		String _osName = packet.readString();
		packet.readString();
		boolean _isDebugger = packet.readBoolean();
		packet.readInt();
		packet.readInt();
		int _garbageCollected = packet.readInt();
		int _averageFrameRate = packet.readInt();
		packet.readInt();
	}

}
