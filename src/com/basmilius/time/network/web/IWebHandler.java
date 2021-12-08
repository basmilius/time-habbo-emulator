package com.basmilius.time.network.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.util.Map;

public abstract class IWebHandler
{

	protected BufferedReader input;
	protected DataOutputStream output;

	public abstract void handle(WebServerRequestMethod requestMethod, String path, Map<String, String> queryString);

	public abstract String getContentType();

	public abstract String getContent();

	public abstract int getStatusCode();

	public void setInput(BufferedReader input)
	{
		this.input = input;
	}

	public void setOutput(DataOutputStream output)
	{
		this.output = output;
	}

}
