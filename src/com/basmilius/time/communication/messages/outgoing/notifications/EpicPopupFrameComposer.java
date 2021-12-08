package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class EpicPopupFrameComposer extends MessageComposer
{

	private final String _imageUrl;

	public EpicPopupFrameComposer(String _imageUrl)
	{
		this._imageUrl = _imageUrl;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.EpicPopupFrame);
		response.appendString(this._imageUrl);
		return response;
	}

}
