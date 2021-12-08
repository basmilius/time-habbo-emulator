package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.GuildBadgePart;
import com.basmilius.time.habbohotel.guilds.GuildColor;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;
import java.util.Map;

public class CreateGuildPanelSymbolsComposer extends MessageComposer
{

	private final List<GuildBadgePart> _partsBase;
	private final List<GuildBadgePart> _partsSymbols;
	private final Map<String, List<GuildColor>> _badgeColors;

	public CreateGuildPanelSymbolsComposer(List<GuildBadgePart> _partsBase, List<GuildBadgePart> _partsSymbols, Map<String, List<GuildColor>> _badgeColors)
	{
		this._partsBase = _partsBase;
		this._partsSymbols = _partsSymbols;
		this._badgeColors = _badgeColors;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CreateGuildPanelSymbols);
		response.appendInt(this._partsBase.size());
		for (GuildBadgePart d : this._partsBase)
		{
			response.appendInt(d.getId());
			response.appendString(d.getPartName1());
			response.appendString(d.getPartName2());
		}
		response.appendInt(this._partsSymbols.size());
		for (GuildBadgePart d : this._partsSymbols)
		{
			response.appendInt(d.getId());
			response.appendString(d.getPartName1());
			response.appendString(d.getPartName2());
		}
		response.appendInt(this._badgeColors.get("color").size());
		for (GuildColor c : this._badgeColors.get("color"))
		{
			response.appendInt(c.getId());
			response.appendString(c.getColor());
		}
		response.appendInt(this._badgeColors.get("color2").size());
		for (GuildColor c : this._badgeColors.get("color2"))
		{
			response.appendInt(c.getId());
			response.appendString(c.getColor());
		}
		response.appendInt(this._badgeColors.get("color3").size());
		for (GuildColor c : this._badgeColors.get("color3"))
		{
			response.appendInt(c.getId());
			response.appendString(c.getColor());
		}
		return response;
	}

}
