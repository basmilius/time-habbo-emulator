package com.basmilius.time.habbohotel.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.TimeUtils;

public class ModerationToolUserInfo implements ISerialize
{

	private final Habbo habbo;

	public ModerationToolUserInfo(final Habbo habbo)
	{
		this.habbo = habbo;
		this.habbo.loadSettings();
		this.habbo.loadSubscriptions();
	}

	@Override
	public void serialize(ServerMessage response)
	{
		response.appendInt(this.habbo.getId());
		response.appendString(this.habbo.getUsername());
		response.appendString(this.habbo.getLook());
		response.appendInt((TimeUtils.getUnixTimestamp() - this.habbo.getAccountCreated()) / 60);
		response.appendInt((TimeUtils.getUnixTimestamp() - this.habbo.getLastOnline()) / 60);
		response.appendBoolean(this.habbo.isOnline());
		response.appendInt(this.habbo.getSettings().getSupportChfs());
		response.appendInt(this.habbo.getSettings().getSupportChfsAbusive());
		response.appendInt(this.habbo.getSettings().getSupportCautions());
		response.appendInt(this.habbo.getSettings().getSupportBans());
		response.appendInt(0); // Trade locks?
		response.appendString("Not supported yet"); // Trade locks expired date?
		response.appendString((this.habbo.getSubscriptions().hasSubscription("habbo_club")) ? "Habbo club" : "Normal user");
		response.appendInt(this.habbo.getRank());
		response.appendInt(Bootstrap.getEngine().getGame().getBanManager().getBansCount(this.habbo));
		response.appendString(this.habbo.getMail());
		response.appendString(this.habbo.getRealName());
	}

}
