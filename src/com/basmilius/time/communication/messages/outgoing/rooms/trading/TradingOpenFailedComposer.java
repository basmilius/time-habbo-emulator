package com.basmilius.time.communication.messages.outgoing.rooms.trading;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TradingOpenFailedComposer extends MessageComposer
{

	public enum ErrorCode
	{
		YouAreTrading,
		OtherIsTrading
	}

	private final int errorCode;
	private final String userName;

	public TradingOpenFailedComposer(final ErrorCode errorCode, final String userName)
	{
		this.errorCode = errorCode == ErrorCode.YouAreTrading ? 7 : 8;
		this.userName = userName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.TradingOpenFailed);
		response.appendInt(this.errorCode);
		response.appendString(this.userName);
		return response;
	}

}
