package com.basmilius.time.communication.messages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Event
{

	int id ();

	int secondId () default -1;

}
