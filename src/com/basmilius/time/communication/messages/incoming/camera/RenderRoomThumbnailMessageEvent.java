package com.basmilius.time.communication.messages.incoming.camera;

import com.basmilius.core.compression.ZipCompression;
import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.RenderRoomThumbnail)
public class RenderRoomThumbnailMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final byte[] bytes = packet.readByteArray();
		final byte[] data = ZipCompression.decompress(bytes);

		Bootstrap.getEngine().getLogging().log(RenderRoomThumbnailMessageEvent.class, new String(data));
	}

}
