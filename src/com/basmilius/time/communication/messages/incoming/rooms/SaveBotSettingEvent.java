package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.habbohotel.enums.Dance;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.users.UpdateUnitNameComposer;

@Event(id = Incoming.SaveBotSetting)
public class SaveBotSettingEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int botId = Math.abs(packet.readInt());
		final int settingId = packet.readInt();

		final Bot bot = Bootstrap.getEngine().getGame().getBotManager().getBot(botId);

		if (bot == null)
			return;
		
		if (bot.getRoomBot() == null)
			return;

		if (bot.getOwner().getId() != connection.getHabbo().getId())
			return;

		if (bot.getRoom() == null)
			return;

		if (settingId == 1)
		{ // Copy my look

			bot.setFigure(connection.getHabbo().getLook(), connection.getHabbo().getGender());

		}
		else if (settingId == 2)
		{ // Chat lines

			String[] textSettings = packet.readString().split(";#;");
			String[] lines = textSettings[0].split("\\r");
			boolean autoChat = (textSettings[1].equals("true"));
			int chatDelay = Integer.parseInt(textSettings[2]);
			boolean mixedChat = (textSettings[3].equals("true"));

			bot.setChatSettings(lines, autoChat, chatDelay, mixedChat);

		}
		else if (settingId == 3)
		{ // MoveAvatar / Stand

		}
		else if (settingId == 4)
		{ // Dance!

			if (bot.getRoomBot().getCurrentDance() == 0)
			{
				bot.getRoomBot().dance(Dance.HAB_HOP);
			}
			else
			{
				bot.getRoomBot().dance(Dance.NONE);
			}

		}
		else if (settingId == 5)
		{ // Change bot name

			String botName = packet.readString();

			bot.setName(botName);

			bot.getRoom().getRoomUnitsHandler().send(new UpdateUnitNameComposer(bot.getRoomBot().getUnitId(), bot.getName()));

		}
	}

}
