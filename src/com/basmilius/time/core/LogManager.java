package com.basmilius.time.core;

import com.basmilius.core.text.WordUtils;
import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.util.TimeUtils;
import com.google.common.base.Strings;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("UnusedDeclaration")
public class LogManager extends IManager
{

	private static final int MaxLength = 120;

	private final IOManager ioManager;
	private boolean paused;

	public LogManager()
	{
		AnsiConsole.systemInstall();
		this.ioManager = new IOManager();
		this.paused = false;
	}

	public final void pause()
	{
		this.paused = true;
	}

	public final void resume()
	{
		this.paused = false;
	}

	public final void clear()
	{
		System.out.flush();
	}

	public final void log(Class<?> logClass, Object log)
	{
		if (this.paused)
			return;

		final String prefix = String.format(" <%s>", logClass.getSimpleName());
		final String newLineString = Strings.padEnd(System.lineSeparator(), prefix.length(), ' ') + "   ";

		System.out.println(Ansi.ansi().render(String.format("@|bold,green %s|@ %s", prefix, WordUtils.wrap(log.toString(), (LogManager.MaxLength - prefix.length()), newLineString, true))));
	}

	public final void logNoNewLine(Class<?> logClass, Object log)
	{
		if (this.paused)
			return;

		final String prefix = String.format(" <%s>", logClass.getSimpleName());
		final String newLineString = Strings.padEnd(System.lineSeparator(), prefix.length(), ' ');

		System.out.print(Ansi.ansi().render(String.format("@|bold,green %s|@ %s", prefix, WordUtils.wrap(log.toString(), (LogManager.MaxLength - prefix.length()), newLineString, true))));
	}

	public final void logFailed()
	{
		System.out.print(Ansi.ansi().render("@|bold,red Failed!|@" + System.lineSeparator()));
	}

	public final void logOK()
	{
		System.out.print(Ansi.ansi().render("@|bold,green Ok!|@" + System.lineSeparator()));
	}

	public final void logLine(final Object line)
	{
		System.out.println(Ansi.ansi().render(line.toString()));
	}

	public final void logDebug(final Object line)
	{
	}

	public final void logDebugLine(final Object line)
	{
	}

	public final void logPacketLine(final Object line, final boolean isUnknown)
	{
		if (!isUnknown && Bootstrap.getEngine().getConfig().getBoolean("debug.show_packets", false))
		{
			this.log(SocketConnection.class, line.toString());
		}
		else if (isUnknown && Bootstrap.getEngine().getConfig().getBoolean("debug.show_unknown_packets", false))
		{
			this.log(SocketConnection.class, line.toString());
		}
	}

	public final void logError(final Object line)
	{
	}

	public final void logErrorLine(final Object line)
	{
	}

	public final void handleException(final Exception e)
	{
		try
		{
			this.ioManager.saveException(e, TimeUtils.getDateString("yyyy-MM-dd") + "-exceptions.log");
			e.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public final void handleSQLException(final SQLException e)
	{
		try
		{
			this.ioManager.saveException(e, TimeUtils.getDateString("yyyy-MM-dd") + "-sql-exceptions.log");
			e.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public final void logBlank()
	{
		System.out.println();
	}

	@Override
	public final void initialize()
	{

	}

	@Override
	public final void dispose()
	{

	}

}
