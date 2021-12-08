package com.basmilius.time.util;

import com.basmilius.time.Bootstrap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilities for parsing seconds into Time strings.
 */
public class TimeUtils
{

	public static String getDateString(final String format)
	{
		final Date date = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static int getUnixTimestamp()
	{
		return (int) (System.currentTimeMillis() / 1000L);
	}

	public static String getUpTime(int seconds)
	{
		if (seconds < 60)
			return String.format(Bootstrap.getEngine().getConfig().getString("dt.str.seconds", "%d seconds"), seconds);
		else if (seconds < 3600)
		{
			final int min = (int) Math.floor(seconds / 60);
			final int sec = seconds - (min * 60);

			return String.format(Bootstrap.getEngine().getConfig().getString("dt.str.minutes.seconds", "%d minutes and %d seconds"), min, sec);
		}
		else if (seconds < 86400)
		{
			final int hou = (int) Math.floor(seconds / 3600);
			seconds = (seconds - (hou * 3600));
			final int min = (int) Math.floor(seconds / 60);
			seconds = (seconds - (min * 60));
			final int sec = seconds;

			return String.format(Bootstrap.getEngine().getConfig().getString("dt.str.hours.minutes.seconds", "%d hours, %d minutes and %d seconds"), hou, min, sec);
		}
		else
		{
			final int day = (int) Math.floor(seconds / 86400);
			seconds = (seconds - (day * 86400));
			final int hou = (int) Math.floor(seconds / 3600);
			seconds = (seconds - (hou * 3600));
			final int min = (int) Math.floor(seconds / 60);
			seconds = (seconds - (min * 60));
			final int sec = seconds;

			return String.format(Bootstrap.getEngine().getConfig().getString("dt.str.days.hours.minutes.seconds", "%d days, %d hours, %d minutes and %d seconds"), day, hou, min, sec);
		}
	}

	public static String unixTimestampToString(int timestamp, String format)
	{
		final long time = (Long.parseLong(Integer.toString(timestamp)) * 1000);
		final Date d = new Date();
		d.setTime(time);
		return new SimpleDateFormat(format).format(d);
	}

	public static String unixTimestampToStringWithMonths(int timestamp, String format)
	{
		String date = unixTimestampToString(timestamp, format);
		final String[] parts = date.split("\\-");
		final String[] months = Bootstrap.getEngine().getConfig().getString("server.date.months", "jan,feb,mar,apr,may,jun,jul,aug,sep,okt,nov,dec").split(",");

		date = (parts[0] + " " + months[(Integer.parseInt(parts[1]) - 1)] + " " + parts[2]);

		return date;
	}

}
