package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class AddEffectItemComposer extends MessageComposer
{

	public static final int TAB_SPECIAL_EFFECTS = 0;
	public static final int TAB_COSTUMES = 1;

	private final int type;
	private final int tab;
	private final int duration;
	private final boolean permanent;

	public AddEffectItemComposer(final int type, final int tab, final int duration, final boolean permanent)
	{
		this.type = type;
		this.tab = tab;
		this.duration = duration;
		this.permanent = permanent;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.AddEffectItem);
		response.appendInt(this.type);
		response.appendInt(this.tab);
		response.appendInt(this.duration);
		response.appendBoolean(this.permanent);
		return response;
	}

}
