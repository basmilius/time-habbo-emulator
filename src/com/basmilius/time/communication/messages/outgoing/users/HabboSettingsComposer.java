package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboSettingsComposer extends MessageComposer
{

	private final Habbo habbo;

	public HabboSettingsComposer(final Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HabboSettings);
		response.appendInt(this.habbo.getSettings().getSystemVolume());
		response.appendInt(this.habbo.getSettings().getFurniVolume());
		response.appendInt(this.habbo.getSettings().getTraxVolume());
		response.appendBoolean(this.habbo.getSettings().getPreferOldChat());
		response.appendBoolean(this.habbo.getSettings().getIgnoreRoomInvites());
		response.appendBoolean(this.habbo.getSettings().getDontFocusOnOwnAvatar());
		response.appendInt(this.habbo.getSettings().getToolbarState());
		response.appendInt(this.habbo.getSettings().getLatestChatBubble());
		return response;
	}

}
