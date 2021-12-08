package com.basmilius.time.habbohotel.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomSerializer;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OfficialItem implements ISerialize
{

	private int id;
	private int parentId;
	private String label;
	private String description;
	private String imagePath;
	private int imageType;
	private int roomId;
	private boolean isCategory;

	public OfficialItem(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.parentId = result.getInt("parent_id");
		this.label = result.getString("caption");
		this.description = result.getString("description");
		this.imagePath = result.getString("image_url");
		this.imageType = ((result.getString("image_type").equalsIgnoreCase("small")) ? 1 : 0);
		this.roomId = result.getInt("room_id");
		this.isCategory = (this.roomId == 0);
	}

	int getId()
	{
		return this.id;
	}

	public int getParentId()
	{
		return this.parentId;
	}

	public String getLabel()
	{
		if (!this.isCategory)
			return this.getRoom().getRoomData().getRoomName();

		return this.label;
	}

	public String getDescription()
	{
		if (!this.isCategory)
			return this.getRoom().getRoomData().getRoomDescription();

		return this.description;
	}

	public String getImagePath()
	{
		return this.imagePath;
	}

	public boolean getIsCategory()
	{
		return this.isCategory;
	}

	public Room getRoom()
	{
		return Bootstrap.getEngine().getGame().getRoomManager().getRoom(this.roomId);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendInt(this.id);
		response.appendString(this.getLabel());
		response.appendString(this.getDescription());
		response.appendInt(this.imageType);
		response.appendString(this.getLabel());
		response.appendString(this.getImagePath());
		response.appendInt((this.getParentId() > 0) ? this.getParentId() : 0);
		response.appendInt((this.getRoom() != null) ? this.getRoom().getRoomUnitsHandler().getUsers().size() : 0);
		response.appendInt((this.getIsCategory()) ? 4 : 2);
		if (this.getIsCategory())
		{
			response.appendBoolean(false);
			final List<OfficialItem> items = Bootstrap.getEngine().getGame().getNavigatorManager().getOfficials(this.getId());
			for (OfficialItem item : items)
			{
				item.serialize(response);
			}
		}
		else
		{
			response.appendBody(new RoomSerializer(this.getRoom()));
		}
	}

}
