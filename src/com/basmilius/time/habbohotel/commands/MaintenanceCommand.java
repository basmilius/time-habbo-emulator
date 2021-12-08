package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.general.HotelClosingComposer;
import com.basmilius.time.communication.messages.outgoing.general.HotelClosingNoDurationComposer;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.maintenance",
		syntax = ":maintenance <minutes>"
)
public class MaintenanceCommand extends ICommand
{

	public MaintenanceCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_shutdown"))
		{
			int duration = -1;
			int minutes = 5;

			if (parts.length > 1 && ObjectUtils.isNumeric(parts[1]))
				minutes = Integer.parseInt(parts[1]);

			if (parts.length > 2 && ObjectUtils.isNumeric(parts[2]))
				duration = Integer.parseInt(parts[2]);

			if (duration > -1)
				Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new HotelClosingComposer(minutes, duration));
			else
				Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new HotelClosingNoDurationComposer(minutes));
			return true;
		}
		return false;
	}

}
