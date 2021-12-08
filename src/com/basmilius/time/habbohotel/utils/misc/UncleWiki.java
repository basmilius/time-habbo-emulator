package com.basmilius.time.habbohotel.utils.misc;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.habbohotel.rooms.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UncleWiki extends Bot
{

	public UncleWiki(final int id, final Room room)
	{
		super(id, null, room, "Oom Wiki", "Piloot bij Wikports Airport", "hd-185-8.hr-831-41.ch-3022-64-91.sh-295-110.fa-1206-64.ea-1403-92.lg-275-92", "M", 0, 0, 0, 0, true, true, 30000, getChatLines("/resources/misc/uncle-wiki.chat-lines"));
	}

	@SuppressWarnings("ConstantConditions")
	private static String[] getChatLines(final String fileName)
	{
		final List<String> chatLines = new ArrayList<>();

		try
		{
			final InputStream fileStream = UncleWiki.class.getResourceAsStream(fileName);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));

			String line;
			while((line = reader.readLine()) != null)
			{
				chatLines.add(line);
			}
		}
		catch (IOException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}

		return chatLines.toArray(new String[chatLines.size()]);
	}

}
