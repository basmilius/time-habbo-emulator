package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.habbo.HabboWardrobeLook;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.util.ObjectUtils;

import java.security.SecureRandom;
import java.util.List;

@Command(
		executable = "cmd.syntax.wardrobe",
		syntax = ":wardrobe [random/1-10]"
)
public class WardrobeCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public WardrobeCommand (final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	/**
	 * Handlres the command.
	 *
	 * @param parts Command arguments.
	 *
	 * @return boolean
	 *
	 * @throws Exception
	 */
	@Override
	public boolean handleCommand (final String[] parts) throws Exception
	{
		if (this.isUser())
		{
			final RoomUser roomUser = this.getInvoker(RoomUser.class);
			final List<HabboWardrobeLook> habboLooks = roomUser.getHabbo().getSettings().getWardrobe().getLooks();

			if (parts.length > 1)
			{
				final int slotId;

				if (ObjectUtils.isNumeric(parts[1]))
				{
					slotId = Integer.parseInt(parts[1]) - 1;
				}
				else if (parts[1].equalsIgnoreCase("random"))
				{
					slotId = (new SecureRandom()).nextInt(habboLooks.size());
				}
				else
				{
					return true;
				}

				final HabboWardrobeLook habboLook = habboLooks.get(slotId % habboLooks.size());
				roomUser.getHabbo().setLook(habboLook.getFigure(), habboLook.getGender(), roomUser.getHabbo().getMotto());
			}

			return true;
		}
		return false;
	}

}
