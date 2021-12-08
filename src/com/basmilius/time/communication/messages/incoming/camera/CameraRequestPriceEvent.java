package com.basmilius.time.communication.messages.incoming.camera;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.camera.CameraPhotoPriceComposer;

@Event(id = Incoming.CameraRequestPrice)
public class CameraRequestPriceEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		/**
		 * TODO: Configurate a key in config.properties for the price of a photo.
		 */
		connection.send(new CameraPhotoPriceComposer(3, 1));
	}

}
