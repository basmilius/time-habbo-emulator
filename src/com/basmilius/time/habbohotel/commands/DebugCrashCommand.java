package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;

@Command(
		executable = ":debug.crash",
		syntax = ":debug.crash"
)
public class DebugCrashCommand extends ICommand
{

	public DebugCrashCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (Bootstrap.getEngine().getConfig().getBoolean("debug.mode", false))
		{
			this.invoker.getRoom().crash();
			return true;
		}

		return false;
	}

}
