package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.GuildBadgePart;
import com.basmilius.time.habbohotel.guilds.GuildColor;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.CreateGuildPanelSymbolsComposer;
import com.basmilius.time.communication.messages.outgoing.users.FuserightComposer;

import java.util.List;
import java.util.Map;

@Event(id = Incoming.LoadCreateGuildPanelSymbols)
public class LoadCreateGuildPanelSymbolsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<GuildBadgePart> partsBase = Bootstrap.getEngine().getGame().getGuildManager().getPartsBase();
		final List<GuildBadgePart> partsSymbols = Bootstrap.getEngine().getGame().getGuildManager().getPartsSymbols();
		final Map<String, List<GuildColor>> badgeColors = Bootstrap.getEngine().getGame().getGuildManager().getPartsColors();

		connection.send(new CreateGuildPanelSymbolsComposer(partsBase, partsSymbols, badgeColors));
		connection.send(new FuserightComposer(connection.getHabbo()));
	}

}
