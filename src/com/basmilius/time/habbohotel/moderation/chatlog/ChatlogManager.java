package com.basmilius.time.habbohotel.moderation.chatlog;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChatlogManager
{

	private final List<Chatlog> _chatlogs;

	public ChatlogManager()
	{
		this._chatlogs = new ArrayList<>();
	}

	public List<Chatlog> getChatlogsFromHabbo(Habbo habbo)
	{
		return this._chatlogs.stream().filter(log -> log.getHabbo().getId() == habbo.getId()).collect(Collectors.toList());
	}

	public List<Chatlog> getChatlogsBetween(Habbo habbo1, Habbo habbo2)
	{
		return this._chatlogs.stream().filter(log -> (log.getHabbo().getId() == habbo1.getId() && log.getHabboTo().getId() == habbo2.getId()) || (log.getHabbo().getId() == habbo2.getId() && log.getHabboTo().getId() == habbo1.getId())).collect(Collectors.toList());
	}

	public List<Chatlog> getChatlogsForRoom(Room room)
	{
		return this._chatlogs.stream().filter(log -> log.getRoom().getRoomData().getId() == room.getRoomData().getId()).collect(Collectors.toList());
	}

	void addChatlog(Chatlog log)
	{
		this._chatlogs.add(log);
	}

	public void addChatlog(Habbo habbo, Room room, String message)
	{
		Chatlog log = new Chatlog(habbo, null, room, TimeUtils.getUnixTimestamp(), message);
		this.addChatlog(log);
	}

	public void addChatlog(Habbo habbo, Habbo habboTo, String message)
	{
		Chatlog log = new Chatlog(habbo, habboTo, null, TimeUtils.getUnixTimestamp(), message);
		this.addChatlog(log);
	}

	public void addChatlog(Habbo habbo, Habbo habboTo, Room room, String message)
	{
		Chatlog log = new Chatlog(habbo, habboTo, room, TimeUtils.getUnixTimestamp(), message);
		this.addChatlog(log);
	}

}
