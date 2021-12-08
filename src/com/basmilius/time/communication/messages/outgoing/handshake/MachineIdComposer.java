package com.basmilius.time.communication.messages.outgoing.handshake;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class MachineIdComposer extends MessageComposer
{

	private String machineId;

	public MachineIdComposer(String machineId)
	{
		this.machineId = machineId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.MachineId);
		response.appendString(this.machineId);
		return response;
	}

}
