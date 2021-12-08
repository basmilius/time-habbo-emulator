package com.basmilius.time.communication.messages.outgoing.pets;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PetCantGoToNestComposer extends MessageComposer
{

	public static final int NO_NESTS = 0;
	public static final int NO_VALID_NESTS = 1;
	public static final int PET_DOES_NOT_FIT = 2;
	public static final int SECOND_PET_OWNER_NEST = 3;
	public static final int PET_ALREADY_IN_NEST = 4;
	public static final int PET_CANT_FIND_NEST = 5;
	public static final int PET_TO_SLEEPY = 6;

	private int reason;

	public PetCantGoToNestComposer(int reason)
	{
		this.reason = reason;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PetCantGoToNest);
		response.appendInt(this.reason);
		return response;
	}

}
