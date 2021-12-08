package com.basmilius.time.communication.messages.outgoing.camera;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CameraPhotoPriceComposer extends MessageComposer
{

	private final int credits;
	private final int duckets;

	public CameraPhotoPriceComposer(final int credits, final int duckets)
	{
		this.credits = credits;
		this.duckets = duckets;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.CameraPhotoPrice);
		response.appendInt(this.credits);
		response.appendInt(this.duckets);
		return response;
	}

}
