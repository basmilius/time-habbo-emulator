package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = ":test",
		syntax = ":test <packet-details ...>"
)
public class TestCommand extends ICommand
{

	public TestCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.invoker.getName().equalsIgnoreCase("bas"))
		{
			try
			{
				String id = parts[1];

				ServerMessage sm = new ServerMessage();
				sm.init(Integer.parseInt(id));

				for (int i = 2; i < parts.length; i++)
				{
					String part = parts[i];

					if (ObjectUtils.isNumeric(part))
					{
						sm.appendInt(Integer.parseInt(part));
					}
					else if (part.equals("false") || part.equals("true"))
					{
						sm.appendBoolean(part.equals("true"));
					}
					else
					{
						if (part.startsWith("I:"))
						{
							part = part.replace("I:", "");
						}
						sm.appendString(part);
					}
				}

				((RoomUser) this.invoker).getConnection().send(sm);
			}
			catch (Exception e)
			{
				// Wrong syntaxis or something, IT'S JUST FOR DEBUG!!
			}
			return true;
		}
		return false;
	}

}
