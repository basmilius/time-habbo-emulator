package com.basmilius.time.habbohotel.utils.avatar;

public class AvatarEditorPart
{

	private final int partId;
	private final String partType;

	public AvatarEditorPart(final int partId, final String partType)
	{
		this.partId = partId;
		this.partType = partType;
	}

	public final int getPartId()
	{
		return this.partId;
	}

	public final String getPartType()
	{
		return this.partType;
	}

}
