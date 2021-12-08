package com.basmilius.time.network.web;

import com.basmilius.time.network.web.handlers.PostServerCommandWebHandler;
import com.basmilius.time.network.web.handlers.StatusWebHandler;
import com.basmilius.time.network.web.handlers.WhosOnlineWebHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class WebServerHandler implements Runnable
{

	private final Map<String, Class<? extends IWebHandler>> _handlers;
	private final BufferedReader _input;
	private final DataOutputStream _output;

	public WebServerHandler(BufferedReader _input, DataOutputStream _output)
	{
		this._handlers = new HashMap<>();
		this._handlers.put("/status.json", StatusWebHandler.class);
		this._handlers.put("/whosonline.json", WhosOnlineWebHandler.class);
		this._handlers.put("/server/command.json", PostServerCommandWebHandler.class);

		this._input = _input;
		this._output = _output;

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run()
	{
		WebServerRequestMethod _requestMethod = WebServerRequestMethod.NONE;
		String _tmp;
		String _method;
		String _path;
		Map<String, String> _queryString = new HashMap<>();
		try
		{
			_tmp = this._input.readLine();

			if (_tmp == null)
			{
				this._output.writeBytes(this.generateResponseHeaders(503, "text/html"));
				this._output.close();
				return;
			}

			_method = _tmp.split(" ")[0];
			_path = _tmp.split(" ")[1].split(" ")[0];

			while (_path.contains("//"))
			{
				_path = _path.replace("//", "/");
			}

			if (_path.contains("?"))
			{
				String _tmp2 = _path.split("\\?")[1];
				String[] _tmp3 = _tmp2.split("&");
				_path = _path.split("\\?")[0];

				for (String _param : _tmp3)
				{
					String[] _data = _param.split("=");

					_queryString.put(_data[0], (_data.length > 1) ? _data[1] : "");
				}
			}

			if (_method.equalsIgnoreCase("GET"))
			{
				_requestMethod = WebServerRequestMethod.GET;
			}
			else if (_method.equalsIgnoreCase("POST"))
			{
				_requestMethod = WebServerRequestMethod.POST;
			}
			else if (_method.equalsIgnoreCase("HEAD"))
			{
				_requestMethod = WebServerRequestMethod.HEAD;
			}

			if (this._handlers.containsKey(_path))
			{
				IWebHandler _handler = this._handlers.get(_path).newInstance();
				_handler.handle(_requestMethod, _path, _queryString);

				this._output.writeBytes(this.generateResponseHeaders(_handler.getStatusCode(), _handler.getContentType()));
				this._output.writeBytes(_handler.getContent());
				this._output.close();
			}
			else
			{
				this._output.writeBytes(this.generateResponseHeaders(404, "text/html"));
				this._output.writeBytes("Not found.");
				this._output.close();
			}
		}
		catch (IOException | InstantiationException | IllegalAccessException ignored)
		{

		}
		finally
		{
			try
			{
				this._output.close();
			}
			catch (IOException ignored)
			{
			}
		}
	}

	String generateResponseHeaders(int _statusCode, String _contentType)
	{
		String responseHeaders = "HTTP/1.1 ";

		switch (_statusCode)
		{
			case 200:
				responseHeaders += "200 OK";
				break;
			case 400:
				responseHeaders += "400 Bad Request";
				break;
			case 401:
				responseHeaders += "401 Unauthorized";
				break;
			case 403:
				responseHeaders += "403 Forbidden";
				break;
			case 404:
				responseHeaders += "404 Not Found";
				break;
			case 405:
				responseHeaders += "405 Method Not Allowed";
				break;
			case 500:
				responseHeaders += "500 Internal Server Error";
				break;
			case 503:
				responseHeaders += "503 Service Unavailable";
				break;
		}

		responseHeaders += "\r\n";
		responseHeaders += "Cache-Control: no-store, no-cache, must-revalidate, post-check=0, pre-check=0\r\n";
		responseHeaders += "Connection: close\r\n";
		responseHeaders += "Pragma: no-cache\r\n";
		responseHeaders += "Connection: close\r\n";
		responseHeaders += "Connection: close\r\n";
		responseHeaders += "Connection: close\r\n";
		responseHeaders += "Content-Type: " + _contentType + "\r\n";
		responseHeaders += "Server: PixelEMU by Bas\r\n";
		responseHeaders += "\r\n";

		return responseHeaders;
	}

}
