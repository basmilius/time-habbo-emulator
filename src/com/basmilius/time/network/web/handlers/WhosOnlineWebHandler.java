package com.basmilius.time.network.web.handlers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.network.web.IWebHandler;
import com.basmilius.time.network.web.WebServerRequestMethod;
import com.basmilius.time.util.json.JSONArray;
import com.basmilius.time.util.json.JSONObject;

import java.util.Map;

public class WhosOnlineWebHandler extends IWebHandler
{

	private String _contents = "";

	@Override
	public void handle(WebServerRequestMethod _requestMethod, String _path, Map<String, String> _queryString)
	{
		final JSONObject jsonContent = new JSONObject();
		final JSONArray onlineUsers = new JSONArray();

		for (SocketConnection connection : Bootstrap.getEngine().getServer().getClientManager().getSessions().values())
		{
			if (!(connection != null && connection.getHabbo() != null))
				continue;

			onlineUsers.put(connection.getHabbo());
		}

		jsonContent.put("users", onlineUsers);
		_contents = jsonContent.toString();
	}

	@Override
	public String getContentType()
	{
		return "application/json";
	}

	@Override
	public String getContent()
	{
		return _contents;
	}

	@Override
	public int getStatusCode()
	{
		return 200;
	}

}
