package com.basmilius.time.communication.messages.outgoing.soundmachine;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SoundMachinePlayListFullComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SoundMachinePlayListFull);
		return response;
	}

}
