package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.util.ObjectUtils;

import java.util.HashMap;

public class ChatCommandsManager extends IManager
{

	private final HashMap<String, Class<? extends ICommand>> commands;

	public ChatCommandsManager()
	{
		this.commands = new HashMap<>();
	}

	public void registerCommand(Class<? extends ICommand> command)
	{
		Command commandInfo = command.getAnnotation(Command.class);
		if (commandInfo != null)
		{
			String commandExecutable = ((commandInfo.executable().startsWith(":") || commandInfo.encrypted()) ? commandInfo.executable() : Bootstrap.getEngine().getConfig().getString(commandInfo.executable(), commandInfo.executable()));

			if (commandInfo.encrypted())
			{
				commandExecutable = StringCrypt.decodeBasArray(ObjectUtils.stringToIntArray(commandExecutable.split(",")));
			}

			if (commandExecutable != null)
			{
				this.commands.put(commandExecutable, command);
			}
			else
			{
				Bootstrap.getEngine().getLogging().logErrorLine(commandInfo.executable() + " is not founded!");
			}
		}
		else
		{
			Bootstrap.getEngine().getLogging().logErrorLine(command.getCanonicalName() + " is not annotated with Command.");
		}
	}

	@SafeVarargs
	public final void registerCommands(Class<? extends ICommand>... commands)
	{
		for (Class<? extends ICommand> command : commands)
		{
			this.registerCommand(command);
		}
	}

	public boolean commandExists(String command)
	{
		return this.commandExists(command.split(" "));
	}

	public boolean commandExists(String[] command)
	{
		return (command.length > 0 && this.commands.containsKey(command[0]));
	}

	public boolean handleCommand(RoomUnit invoker, String text, boolean shouted, int bubbleId)
	{
		String[] parts = text.split(" ");

		if (this.commandExists(parts))
		{
			Class<? extends ICommand> command = this.commands.get(parts[0]);
			try
			{
				ICommand handler = command.getConstructor(RoomUnit.class, boolean.class, int.class).newInstance(invoker, shouted, bubbleId);

				if (!handler.getAllowBots() && invoker.getUnitType() == RoomUnitType.BOT)
					return false;

				if (Bootstrap.getEngine().getConfig().getBoolean("hotel.disable.commands", false))
					return false;

				Bootstrap.getEngine().getLogging().logDebugLine(((invoker.getUnitType() == RoomUnitType.USER) ? "User" : (invoker.getUnitType() == RoomUnitType.BOT) ? "Bot" : "Pet") + " " + invoker.getName() + " executed command " + text);

				return handler.handleCommand(parts);
			}
			catch (CommandNotAvailableException e)
			{
				if (invoker.getUnitType() == RoomUnitType.USER)
				{
					SocketConnection connection = ((RoomUser) invoker).getConnection();

					String msg = "";

					msg += "<font color=\"#C50707\" size=\"12\"><b>Tijdelijk niet beschikbaar</b></font>" + (char) 13;
					msg += "Het '%s' commando is tijdelijk niet beschikbaar. De server gaf de volgende informatie mee:" + (char) 13;
					msg += "%s";

					connection.sendNotif(GenericAlertType.TEXT, String.format(msg, e.getCommand().getClass().getSimpleName(), e.getMessage()));
				}
				return true;
			}
			catch (Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
				return false;
			}
		}

		return false;
	}

	public String toString(String[] parts, Integer startAt)
	{
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < parts.length; i++)
		{
			if (i < startAt)
				continue;
			builder.append(parts[i]).append(" ");
		}

		String output = builder.toString();
		output = output.substring(0, output.length() - 1);
		return output;
	}

	@Override
	public void initialize() throws InstantiationException, IllegalAccessException
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(ChatCommandsManager.class, "Loading game commands .. ");

		this.registerCommand(AboutCommand.class);
		this.registerCommand(AlertCommand.class);
		this.registerCommand(AmbassadorCommand.class);
		this.registerCommand(BadgeGiveCommand.class);
		this.registerCommand(BanCommand.class);
		this.registerCommand(CarryCommand.class);
		this.registerCommand(CommandsCommand.class);
		this.registerCommand(CreditsCommand.class);
		this.registerCommand(DisconnectCommand.class);
		this.registerCommand(EjectAllCommand.class);
		this.registerCommand(EnableCommand.class);
		this.registerCommand(HabNamCommand.class);
		this.registerCommand(HotelAlertCommand.class);
		this.registerCommand(InfoCommand.class);
		this.registerCommand(KickAllCommand.class);
		this.registerCommand(MaintenanceCommand.class);
		this.registerCommand(MoonwalkCommand.class);
		this.registerCommand(MuteCommand.class);
		this.registerCommand(MotdCommand.class);
		this.registerCommand(PickAllCommand.class);
		this.registerCommand(PullCommand.class);
		this.registerCommand(PushCommand.class);
		this.registerCommand(RestartCommand.class);
		this.registerCommand(SetPublicCommand.class);
		this.registerCommand(ShutdownCommand.class);
		this.registerCommand(SitCommand.class);
		this.registerCommand(SpectatorCommand.class);
		this.registerCommand(StandCommand.class);
		this.registerCommand(SummonCommand.class);
		this.registerCommand(TalkCommand.class);
		this.registerCommand(TeleportCommand.class);
		this.registerCommand(UnloadCommand.class);
		this.registerCommand(UpdateCatalogueCommand.class);
		this.registerCommand(UpdateConfigCommand.class);
		this.registerCommand(UpdateItemsCommand.class);
		this.registerCommand(UpdateYouTubeCommand.class);
		this.registerCommand(WardrobeCommand.class);
		this.registerCommand(WarpCommand.class);
		this.registerCommand(WhosInGuildCommand.class);
		this.registerCommand(WhosInRoomCommand.class);
		this.registerCommand(WhosOnlineCommand.class);

		if (Bootstrap.getEngine().getConfig().getBoolean("debug.mode", false))
		{
			this.registerCommands(DebugCrashCommand.class, TestCommand.class);
		}

		Bootstrap.getEngine().getLogging().logOK();
	}

	@Override
	public void dispose()
	{
		this.commands.clear();
	}
}
