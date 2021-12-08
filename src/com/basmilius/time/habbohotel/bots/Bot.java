package com.basmilius.time.habbohotel.bots;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomBot;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomUserComposer;
import com.basmilius.time.communication.messages.outgoing.users.UpdateLookInRoomComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A BOT unit.
 */
public class Bot extends ISave
{

	private final int id;
	private final Habbo owner;
	private Room room;
	private String name;
	private String motto;
	private String figure;
	private String gender;
	private int x;
	private int y;
	private double z;
	private int rot;
	private boolean chatAuto;
	private boolean chatMixed;
	private int chatDelay;
	private String[] chatLines;

	/**
	 * Constructor.
	 *
	 * @param result The data from the Database.
	 * @throws SQLException
	 */
	public Bot(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.owner = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(result.getInt("room_id"));
		this.name = result.getString("name");
		this.motto = result.getString("motto");
		this.figure = result.getString("figure");
		this.gender = result.getString("gender");
		this.x = result.getInt("x");
		this.y = result.getInt("y");
		this.z = result.getDouble("z");
		this.rot = result.getInt("rot");
		this.chatAuto = (result.getString("chat_auto").equals("1"));
		this.chatMixed = (result.getString("chat_mixed").equals("1"));
		this.chatDelay = result.getInt("chat_delay");
		this.chatLines = result.getString("chat_lines").split(Character.toString((char) 1));

		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.bots", 30000));
	}

	public Bot(final int id, final Habbo owner, final Room room, final String name, final String motto, final String figure, final String gender, final int x, final int y, final double z, final int rot, final boolean chatAuto, final boolean chatMixed, final int chatDelay, final String[] chatLines)
	{
		this.id = id;
		this.owner = owner;
		this.room = room;
		this.name = name;
		this.motto = motto;
		this.figure = figure;
		this.gender = gender;
		this.x = x;
		this.y = y;
		this.z = z;
		this.rot = rot;
		this.chatAuto = chatAuto;
		this.chatMixed = chatMixed;
		this.chatDelay = chatDelay;
		this.chatLines = chatLines;

		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.bots", 30000));
	}

	/**
	 * Gets the Bot's ID.
	 *
	 * @return int
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Gets the owner of this Bot.
	 *
	 * @return Habbo
	 */
	public Habbo getOwner()
	{
		return this.owner;
	}

	/**
	 * Gets the Room where this Bot is in.
	 *
	 * @return Room
	 */
	public Room getRoom()
	{
		return this.room;
	}

	/**
	 * Gets the name of this Bot.
	 *
	 * @return String
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of this Bot.
	 *
	 * @param name New name for this Bot.
	 */
	public void setName(final String name)
	{
		if (this.name.equals(name))
			return;

		this.name = name;
		this.setNeedsUpdate(true);

		this.room.getRoomUnitsHandler().send(new RoomUserComposer(this.getRoomBot()));
	}

	/**
	 * Gets the Motto of this Bot.
	 *
	 * @return String
	 */
	public String getMotto()
	{
		return this.motto;
	}

	/**
	 * Gets the Figure of this Bot.
	 *
	 * @return String
	 */
	public String getLook()
	{
		return this.figure;
	}

	/**
	 * Gets the Gender of this Bot.
	 *
	 * @return String
	 */
	public String getGender()
	{
		return this.gender;
	}

	/**
	 * Gets the X-Position of this Bot.
	 *
	 * @return int
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Gets the Y-Position of this Bot.
	 *
	 * @return int
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Gets the Z-Position of this Bot.
	 *
	 * @return double
	 */
	public double getZ()
	{
		return this.z;
	}

	/**
	 * Gets the Rotation of this Bot.
	 *
	 * @return int
	 */
	public int getRot()
	{
		return this.rot;
	}

	/**
	 * Gets if this bot can chat auto.
	 *
	 * @return boolean
	 */
	public boolean getChatAuto()
	{
		return this.chatAuto;
	}

	/**
	 * Gets if this bot can chat randomly.
	 *
	 * @return boolean
	 */
	public boolean getChatMixed()
	{
		return this.chatMixed;
	}

	/**
	 * Gets the delay between chat lines.
	 *
	 * @return int
	 */
	public int getChatDelay()
	{
		return this.chatDelay;
	}

	/**
	 * Gets the chat lines.
	 *
	 * @return List
	 */
	public List<String> getChatLines()
	{
		List<String> lines = new ArrayList<>();

		for (final String line : this.chatLines)
		{
			if (line.isEmpty() || line.replace(" ", "").isEmpty())
				continue;

			lines.add(line);
		}

		return lines;
	}

	/**
	 * Gets the RoomBot instance for this Bot.
	 *
	 * @return RoomBot
	 */
	public final RoomBot getRoomBot()
	{
		if (this.room != null)
		{
			final List<RoomBot> users = this.getRoom().getRoomUnitsHandler().getBots().stream().filter(u -> u.getBot() == this).collect(Collectors.toList());
			if (users.size() > 0)
				return users.get(0);
		}
		return null;
	}

	/**
	 * Gets a random chat line.
	 *
	 * @return String
	 */
	public String getRandomChatLine()
	{
		return this.chatLines[this.getRoom().getRandomizer().nextInt(this.chatLines.length)];
	}

	/**
	 * Sets the figure for this bot.
	 *
	 * @param figure New figure.
	 * @param gender New gender.
	 */
	public void setFigure(final String figure, final String gender)
	{
		if (this.figure.equals(figure))
			return;

		this.figure = figure;
		this.gender = gender;
		this.setNeedsUpdate(true);

		if (this.getRoomBot() == null)
			return;
		
		this.room.getRoomUnitsHandler().send(new UpdateLookInRoomComposer(this.getRoomBot().getUnitId(), this.getLook(), this.getGender(), this.getMotto(), 0));
	}

	/**
	 * Sets the chat settings.
	 *
	 * @param lines     Chat lines.
	 * @param autoChat  Auto chat.
	 * @param chatDelay Delay between chat lines.
	 * @param mixedChat Random chat lines.
	 */
	public void setChatSettings(final String[] lines, final boolean autoChat, final int chatDelay, final boolean mixedChat)
	{
		this.chatLines = lines;
		this.chatAuto = autoChat;
		this.chatDelay = chatDelay;
		this.chatMixed = mixedChat;
		this.setNeedsUpdate(true);
	}

	/**
	 * Gets all the chat lines as a String.
	 *
	 * @return String
	 */
	private String getChatLinesString()
	{
		String result = "";
		boolean isFirst = true;

		for (final String line : this.chatLines)
		{
			if (!isFirst)
			{
				result += Character.toString((char) 1);
			}

			result += line;

			isFirst = false;
		}

		return result;
	}

	public final void setRoom(final Room room)
	{
		this.room = room;
		this.setNeedsUpdate(true);
	}

	/**
	 * Executes when needs save.
	 *
	 * @throws SQLException
	 */
	@Override
	public void save() throws SQLException
	{
		if (this.getRoomBot() == null)
			return;
		
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE bots SET room_id = ?, name = ?, motto = ?, figure = ?, gender = ?, x = ?, y = ?, z = ?, rot = ?, chat_lines = ?, chat_auto = ?, chat_mixed = ?, chat_delay = ? WHERE id = ?");
		{
			if (statement != null)
			{
				statement.setInt(1, this.room != null ? this.room.getRoomData().getId() : 0);
				statement.setString(2, this.name);
				statement.setString(3, this.motto);
				statement.setString(4, this.figure);
				statement.setString(5, this.gender);
				statement.setInt(6, (this.room != null ? this.getRoomBot().getX() : 0));
				statement.setInt(7, (this.room != null ? this.getRoomBot().getY() : 0));
				statement.setDouble(8, (this.room != null ? this.getRoomBot().getZ() : 0));
				statement.setInt(9, (this.room != null ? this.getRoomBot().getBodyRotation() : 0));
				statement.setString(10, this.getChatLinesString());
				statement.setString(11, "" + ((this.chatAuto) ? "1" : "0"));
				statement.setString(12, "" + ((this.chatMixed) ? "1" : "0"));
				statement.setInt(13, this.chatDelay);
				statement.setInt(14, this.id);
				statement.executeUpdate();
			}
		}

		this.setNeedsUpdate(false);
	}

}
