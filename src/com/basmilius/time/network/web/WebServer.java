package com.basmilius.time.network.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer implements Runnable
{

	private final boolean _running;
	private ServerSocket _serverSocket;
	private int _port;

	public WebServer()
	{
		this._running = false;
	}

	public void listen()
	{
		try
		{
			this._serverSocket = new ServerSocket(this._port);

			Thread t = new Thread(this);
			t.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private ServerSocket getServerSocket()
	{
		return this._serverSocket;
	}

	public boolean getIsRunning()
	{
		return this._running;
	}

	public void setPort(int _port) throws Exception
	{
		if (this._running)
			throw new Exception("Webserver already running!");

		this._port = _port;
	}

	@Override
	public void run()
	{
		while (true)
		{
			if (getServerSocket().isClosed())
				break;

			try
			{
				Socket _connectionSocket = getServerSocket().accept();

				BufferedReader input = new BufferedReader(new InputStreamReader(_connectionSocket.getInputStream()));
				DataOutputStream output = new DataOutputStream(_connectionSocket.getOutputStream());

				new WebServerHandler(input, output);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

}
