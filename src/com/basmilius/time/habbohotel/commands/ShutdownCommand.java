package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.general.HotelClosedComposer;
import com.basmilius.time.communication.messages.outgoing.general.HotelClosingNoDurationComposer;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.shutdown",
		syntax = ":shutdown [int seconds]"
)
public class ShutdownCommand extends ICommand
{

	public ShutdownCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(final String[] parts) throws Exception
	{
		if (this.isUser() && ((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_shutdown"))
		{
			if (parts.length == 1)
			{
				Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new HotelClosedComposer(0, 0));

				this.shutdown(1);
			}
			else if (ObjectUtils.isNumeric(parts[1]))
			{
				int seconds = Integer.parseInt(parts[1]);

				Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new HotelClosingNoDurationComposer((int) Math.ceil(seconds / 60)));

				this.shutdown(seconds);
			}
			return true;
		}
		return false;
	}

	private void shutdown(final int seconds)
	{
		Thread t = new Thread(() -> {
			try
			{
				final int millis = (seconds * 1000);
				Thread.sleep(millis);
				Bootstrap.getEngine().prepareShutdown();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		});
		t.start();
	}

}
