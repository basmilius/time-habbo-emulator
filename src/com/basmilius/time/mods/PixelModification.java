package com.basmilius.time.mods;

import com.basmilius.time.BootstrapEngine;

import java.util.UUID;

public abstract class PixelModification
{

	private UUID id;
	private final String name;
	private final ModificationType type;

	private BootstrapEngine engine;

	protected PixelModification(final UUID id, final String name, final ModificationType type)
	{
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public abstract void onTimeEmulatorFinishedLoadingMe();

	protected final BootstrapEngine getEngine()
	{
		return this.engine;
	}

	public final UUID getId()
	{
		return this.id;
	}

	public final String getName()
	{
		return this.name;
	}

	public final ModificationType getType()
	{
		return this.type;
	}

	public final void setEngine(final BootstrapEngine engine)
	{
		this.engine = engine;
	}

}
