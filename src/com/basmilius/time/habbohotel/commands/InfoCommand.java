package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;
import com.basmilius.time.util.SystemUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;

@Command(
		executable = "cmd.syntax.info",
		syntax = ":info"
)
public class InfoCommand extends ICommand
{

	public InfoCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("is_staff"))
		{
			String msg = "";

			final OperatingSystemMXBean myOsBean = ManagementFactory.getOperatingSystemMXBean();

			msg += "<font color=\"#C50707\" size=\"12\"><b>Unit information</b></font>" + (char) 13;
			msg += "<b>Data</b>" + (char) 13;
			msg += String.format("Unit-ID: %d", this.invoker.getUnitId()) + (char) 13;
			msg += String.format("Is-Teleporting: %b", this.invoker.getIsTeleporting()) + (char) 13;
			msg += (char) 13;
			msg += "<b>Position</b>" + (char) 13;
			msg += String.format("X: %d, Y: %d, Z: %f", this.invoker.getX(), this.invoker.getY(), this.invoker.getZ()) + (char) 13;
			msg += String.format("Rot: %d, H-Rot: %d", this.invoker.getBodyRotation(), this.invoker.getHeadRotation()) + (char) 13;
			msg += (char) 13;
			msg += "<font color=\"#C50707\" size=\"12\"><b>Room information</b></font>" + (char) 13;
			msg += "<b>Data</b>" + (char) 13;
			msg += String.format("ID: %d", this.invoker.getRoom().getRoomData().getId()) + (char) 13;
			msg += String.format("Owner: %s, ID: %d", this.invoker.getRoom().getRoomData().getOwner().getUsername(), this.invoker.getRoom().getRoomData().getOwner().getId()) + (char) 13;
			msg += String.format("Room name: %s", this.invoker.getRoom().getRoomData().getRoomName()) + (char) 13;
			msg += String.format("Model name: %s", this.invoker.getRoom().getRoomData().getRoomModel().getId()) + (char) 13;
			msg += (char) 13;
			msg += "<font color=\"#C50707\" size=\"12\"><b>Server information</b></font>" + (char) 13;
			msg += "<b>System info</b>" + (char) 13;
			msg += String.format("Running on %s %s for %s@%s", SystemUtils.getOsName(), System.getProperty("os.arch"), System.getProperty("user.name"), InetAddress.getLocalHost().getHostName()) + (char) 13;
			msg += String.format("Java version: %s", System.getProperty("java.version")) + (char) 13;
			msg += (char) 13;
			msg += "<b>Performance</b>" + (char) 13;
			msg += String.format("CPUs: %d; Architecture: %s", myOsBean.getAvailableProcessors(), myOsBean.getArch()) + (char) 13;
			msg += String.format("Memory usage: %.3fMB", ((Bootstrap.freeMemory() / 1024) / 1024)) + (char) 13;

			((RoomUser) this.invoker).getConnection().send(new GenericAlertComposer(msg));

			return true;
		}
		return false;
	}

}
