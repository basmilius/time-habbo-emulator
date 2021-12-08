package nl.pixeltime.mod.test;

import com.basmilius.time.mods.ModificationType;
import com.basmilius.time.mods.PixelModification;

import java.util.UUID;

public class Bootstrap extends PixelModification
{

	public Bootstrap()
	{
		super(UUID.randomUUID(), "Test", ModificationType.ExtremeProgramming);
	}

	@Override
	public final void onTimeEmulatorFinishedLoadingMe ()
	{
		this.getEngine().getLogging().log(Bootstrap.class, "Hello world from Test Mod!");
	}

}
