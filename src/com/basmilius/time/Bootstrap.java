package com.basmilius.time;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public final class Bootstrap
{

	private static BootstrapEngine engine;

	public static BootstrapEngine getEngine()
	{
		return Bootstrap.engine;
	}

	public static float freeMemory()
	{
		gc();

		final OperatingSystemMXBean myOsBean = ManagementFactory.getOperatingSystemMXBean();
		final double load = myOsBean.getSystemLoadAverage();
		final float freeMemory = (getEngine().getRuntime().totalMemory() - getEngine().getRuntime().freeMemory());

		getEngine().getLogging().log(Bootstrap.class, String.format("CPU usage: %f%%, Memory usage: %.3fMB", Math.abs(load), ((freeMemory / 1024) / 1024)));

		return freeMemory;
	}

	public static void gc()
	{
		getEngine().getRuntime().gc();
	}

	public static void run(String[] args) throws Exception
	{
		Bootstrap.engine = new BootstrapEngine(args);
	}

}