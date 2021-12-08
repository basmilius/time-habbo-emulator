package com.basmilius.time.util;

@SuppressWarnings("unused")
public class SystemUtils
{

	public static String getOsName()
	{
		return System.getProperty("os.name", "Unknown");
	}

	public static boolean isMacOS()
	{
		return getOsName().startsWith("Mac OS X");
	}

	public static boolean isUnix()
	{
		return getOsName().startsWith("Linux") || getOsName().startsWith("SunOS") || getOsName().startsWith("FreeBSD");
	}

	public static boolean isWindows()
	{
		return getOsName().startsWith("Windows");
	}

}
