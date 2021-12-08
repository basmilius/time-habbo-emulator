package com.basmilius.time.communication.messages.outgoing.wired;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WiredRewardComposer extends MessageComposer
{

	public static final int FAILED_LIMITED_REACHED = 0;
	public static final int FAILED_ALREADY_HAVE_REWARD = 1;
	public static final int FAILED_ALREADY_TODAY_REWARD = 2;
	public static final int FAILED_ALREADY_HOUR_REWARD = 3;
	public static final int FAILED_NO_LUCK = 4;
	public static final int FAILED_ALREADY_ALL_REWARDS = 5;
	public static final int SUCCESS_GOT_REWARD = 6;
	public static final int SUCCESS_BADGE_RECEIVED = 7;

	private final int reason;

	public WiredRewardComposer(int reason)
	{
		this.reason = reason;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.WiredReward);
		response.appendInt(this.reason);
		return response;
	}

}
