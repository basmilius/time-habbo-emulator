package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;
import com.basmilius.time.util.TimeUtils;

@Command(
		executable = "cmd.syntax.about",
		syntax = ":about"
)
public class AboutCommand extends ICommand
{

	public AboutCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts)
	{
		String msg = "";

		msg += "<font color=\"#C50707\" size=\"15\"><b>Time Emulator</b></font>" + (char) 13;
		msg += "<i>Mogelijk gemaakt door PixelTime</i>" + (char) 13;
		msg += "" + (char) 13;
		msg += "Fouten en tips kunnen gemeld worden bij het team. Misbruik maken van eventuele fouten en leaks worden bestraft." + (char) 13;
		msg += "Voor vragen kun je ook via de website een bericht sturen via je PixelTime account." + (char) 13;
		msg += "" + (char) 13;
		msg += "<b>Aantal online gebruikers</b>" + (char) 13;
		msg += Integer.toString(Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos().size()) + (char) 13;
		msg += "" + (char) 13;
		msg += "<b>Server uptime</b>" + (char) 13;
		msg += TimeUtils.getUpTime(TimeUtils.getUnixTimestamp() - Bootstrap.getEngine().getTimeStarted());

		((RoomUser) this.invoker).getConnection().send(new GenericAlertComposer(msg));
		return true;
	}

}
