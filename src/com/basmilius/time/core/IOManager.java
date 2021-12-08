package com.basmilius.time.core;

import java.io.*;

@SuppressWarnings("UnusedDeclaration")
public final class IOManager
{

	public IOManager()
	{

	}

	@SuppressWarnings ("ResultOfMethodCallIgnored")
	public final void saveException(final Exception exception, final String fileName) throws IOException
	{
		String exactFileName = "error/" + fileName;
		final StringBuilder sb = new StringBuilder();
		String everything = "";

		final File file = new File(exactFileName);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		exactFileName = file.getAbsolutePath();

		try
		{
			final BufferedReader br = new BufferedReader(new FileReader(exactFileName));
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		}
		catch (IOException ignored)
		{
		}

		final PrintWriter writer = new PrintWriter(exactFileName, "UTF-8");
		writer.write(everything);
		writer.write(System.lineSeparator());
		exception.printStackTrace(writer);
		writer.close();
	}

	public final void saveQuery(final String query, final String fileName) throws IOException
	{
		final String exactFileName = "error/" + fileName;
		final StringBuilder sb = new StringBuilder();
		String everything = "";

		try
		{
			final BufferedReader br = new BufferedReader(new FileReader(exactFileName));
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		}
		catch (IOException ignored)
		{
		}

		final PrintWriter writer = new PrintWriter(exactFileName, "UTF-8");
		writer.write(everything);
		writer.write(System.lineSeparator());
		writer.write(query);
		writer.close();
	}

}
