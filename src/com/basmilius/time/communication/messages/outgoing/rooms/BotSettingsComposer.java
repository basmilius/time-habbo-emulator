package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class BotSettingsComposer extends MessageComposer
{

	private final int _settingId;
	private final Bot _bot;

	public BotSettingsComposer(int _unitId, Bot _bot)
	{
		this._settingId = _unitId;
		this._bot = _bot;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.BotSettings);
		response.appendInt(-this._bot.getId());
		response.appendInt(this._settingId);
		response.appendString(this.getSetting());

		return response;
	}

	String getSetting()
	{
		String setting = "";

		if (this._settingId == 2)
		{
			boolean first = true;

			for (String line : this._bot.getChatLines())
			{
				if (!first)
				{
					setting += "\r";
				}
				setting += line;

				first = false;
			}

			setting += ";#;" + ((this._bot.getChatAuto()) ? "true" : "false");
			setting += ";#;" + this._bot.getChatDelay();
			setting += ";#;" + ((this._bot.getChatMixed()) ? "true" : "false");
		}
		else if (this._settingId == 5)
		{
			setting += this._bot.getName();
		}

		return setting;
	}

}
