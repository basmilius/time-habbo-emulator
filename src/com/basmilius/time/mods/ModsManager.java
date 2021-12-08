package com.basmilius.time.mods;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ModsManager extends IManager
{

	private final List<PixelModification> modifications;

	public ModsManager()
	{
		this.modifications = new ArrayList<>();
	}

	public final List<PixelModification> getModifications()
	{
		return this.modifications;
	}

	@Override
	public void initialize () throws Exception
	{
		final File modsDirectory = new File("scripts/");
		if (!modsDirectory.exists())
			return;

		final File[] mods = modsDirectory.listFiles((dir, name) -> {
			return name.endsWith(".jar");
		});

		Bootstrap.getEngine().getLogging().log(ModsManager.class, String.format("Found %d modifications. Trying to load them now.", mods.length));

		for (final File modFile : mods)
		{
			try
			{
				loadModification(modFile);
			}
			catch (MalformedURLException | ClassNotFoundException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		}
	}

	private void loadModification(final File modificationFile) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		final URL modUrl = modificationFile.toURI().toURL();
		final URL[] modUrls = new URL[]{modUrl};
		final ClassLoader modificationLoader = new URLClassLoader(modUrls);
		final Class modificationClass = modificationLoader.loadClass(String.format("%s.Bootstrap", modificationFile.getName().replace(".jar", "")));
		final PixelModification modification = (PixelModification) modificationClass.newInstance();

		modification.setEngine(Bootstrap.getEngine());

		Bootstrap.getEngine().getLogging().log(ModsManager.class, String.format("Loaded modification %s with ID %s", modification.getName(), modification.getId().toString()));

		modification.onTimeEmulatorFinishedLoadingMe();

		modifications.add(modification);
	}

	@Override
	public void dispose ()
	{
		this.modifications.clear();
	}

}
