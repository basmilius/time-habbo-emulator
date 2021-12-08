package com.basmilius.time.habbohotel.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for Commands.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command
{

	/**
	 * Gets if this command is encrypted with BAS.
	 *
	 * @return boolean
	 */
	boolean encrypted () default false;

	/**
	 * Gets the executable.
	 *
	 * @return String
	 */
	String executable ();

	/**
	 * Gets the syntax of this command.
	 *
	 * @return String
	 */
	String syntax ();

}
