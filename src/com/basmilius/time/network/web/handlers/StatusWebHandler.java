package com.basmilius.time.network.web.handlers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.network.web.IWebHandler;
import com.basmilius.time.network.web.WebServerRequestMethod;
import com.basmilius.time.util.TimeUtils;
import com.basmilius.time.util.json.JSONObject;

import java.util.Map;

public class StatusWebHandler extends IWebHandler
{

	private String _contents = "";

	@Override
	public void handle(WebServerRequestMethod _requestMethod, String _path, Map<String, String> _queryString)
	{
		JSONObject jsonContent = new JSONObject();
		jsonContent.put("online", true);
		jsonContent.put("onlineCount", Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos().size());
		jsonContent.put("uptime", (TimeUtils.getUnixTimestamp() - Bootstrap.getEngine().getTimeStarted()));
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
