package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.catalogue.CatalogueUpdatedComposer;

@Command(
		executable = "cmd.syntax.update.catalogue",
		syntax = ":update_catalogue"
)
public class UpdateCatalogueCommand extends ICommand
{

	public UpdateCatalogueCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_update_catalogue"))
		{
			Bootstrap.getEngine().getGame().getCatalogueManager().reloadCatalogue();
			Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new CatalogueUpdatedComposer());
			return true;
		}
		return false;
	}

}
