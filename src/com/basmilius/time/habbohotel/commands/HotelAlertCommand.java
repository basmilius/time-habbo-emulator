package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;

@Command(
		executable = "cmd.syntax.hotelalert",
		syntax = ":ha <message>"
)
public class HotelAlertCommand extends ICommand
{

	public HotelAlertCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_ha"))
		{
			String alert = Bootstrap.getEngine().getGame().getChatCommands().toString(parts, 1) + (char) 13;
			alert += (char) 13;
			alert += " - " + this.invoker.getName();

			Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new GenericAlertComposer(alert));

			return true;
		}
		return false;
	}

}
