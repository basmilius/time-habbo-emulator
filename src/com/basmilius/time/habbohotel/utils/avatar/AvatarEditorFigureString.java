package com.basmilius.time.habbohotel.utils.avatar;

import java.util.ArrayList;
import java.util.List;

public class AvatarEditorFigureString
{

	public static List<AvatarEditorPart> getParts(final String figureString)
	{
		final List<AvatarEditorPart> parts = new ArrayList<>();
		final String[] fsParts = figureString.split("\\.");

		for (final String fsPart : fsParts)
		{
			final String[] fsData = fsPart.split("\\-");
			if (fsData.length >= 2)
			{
				parts.add(new AvatarEditorPart(Integer.parseInt(fsData[1]), fsData[0]));
			}
		}

		return parts;
	}

}
