package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class AvatarEditorApplyEffectComposer extends MessageComposer
{

	private final int type;
	private final int duration;

	public AvatarEditorApplyEffectComposer(final int type, final int duration)
	{
		this.type = type;
		this.duration = duration;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.AvatarEditorApplyEffect);
		response.appendInt(this.type);
		response.appendInt(this.duration);
		response.appendBoolean(true); // TODO
		return response;
	}

}
