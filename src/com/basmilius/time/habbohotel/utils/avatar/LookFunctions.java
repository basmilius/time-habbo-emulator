package com.basmilius.time.habbohotel.utils.avatar;

import com.basmilius.time.util.ObjectUtils;

public class LookFunctions
{

	public static String getFigureString(String figure, String data)
	{
		String[] parts = figure.split("\\.");
		String finalFigure = "";

		for (String part : parts)
		{
			String[] pData = part.split("\\-");
			if (data.contains(pData[0]))
				continue;
			finalFigure += part + ".";
		}

		return finalFigure.substring(0, (finalFigure.length() - 1));
	}

	public static String getFigureStringAllow(String figure, String data)
	{
		String[] parts = figure.split("\\.");
		String finalFigure = "";

		for (String part : parts)
		{
			String[] pData = part.split("\\-");
			if (!data.contains(pData[0]))
				continue;
			finalFigure += part + ".";
		}

		return finalFigure.substring(0, (finalFigure.length() - 1));
	}

	public static boolean isValidLook(String figure)
	{
		if (figure.length() < 18 || figure.length() > 150)
			return false;

		String[] sets = figure.split("\\.");

		if (!(sets.length <= 13 && sets.length >= 2))
			return false;

		boolean hasHD = false;
		boolean hasLG = false;
		boolean checkOthers = (sets.length > 2);
		boolean hasOthers = false;

		for (String set : sets)
		{
			String[] parts = set.split("\\-");

			if (!(parts.length == 3 || parts.length == 4))
				return false;

			if (!(parts[0].length() == 2 || parts[0].length() == 3))
				return false;

			if (!(ObjectUtils.isNumeric(parts[1]) && ObjectUtils.isNumeric(parts[2])))
				return false;

			int type = Integer.parseInt(parts[1]);
			int color = Integer.parseInt(parts[2]);

			if (type < 1 || color < 0)
				return false;

			for (String part : parts)
			{
				if (part.equals("hd"))
					hasHD = true;

				if (part.equals("lg"))
					hasLG = true;

				if (checkOthers)
				{
					if (part.equals("wa") || part.equals("cc") || part.equals("fa") || part.equals("ca") || part.equals("ch") || part.equals("he") || part.equals("ea") || part.equals("cp") || part.equals("ha") || part.equals("sh"))
						hasOthers = true;
				}
			}
		}

		return !(!hasHD || !hasLG) && !(checkOthers && !hasOthers);

	}

}
