package com.basmilius.time.communication.messages.incoming.room.avatar;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.Sign)
public class SignMessageEvent extends MessageEvent implements Runnable
{

	private int signId = 0;

	@Override
	public void handle()
	{
		if (!connection.getHabbo().isInRoom())
			return;

		this.signId = packet.readInt();

		final Thread signThread = new Thread(this);
		signThread.start();
	}

	@Override
	public void run()
	{
		try
		{
			if (connection.getHabbo().getRoomUser() == null)
				return;
			
			connection.getHabbo().getRoomUser().getStatuses().put("sign", String.valueOf(this.signId));
			connection.getHabbo().getRoomUser().updateStatus();

			Thread.sleep(Bootstrap.getEngine().getConfig().getInt("hotel.timing.sign", 3000));

			connection.getHabbo().getRoomUser().getStatuses().remove("sign");
			connection.getHabbo().getRoomUser().updateStatus();
		}
		catch (InterruptedException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

}
