package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.habbohotel.enums.CategoryType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCategory
{

	private final int _id;
	private final int _minRank;
	private final String _caption;
	private final boolean _canTrade;
	private final CategoryType _categoryType;

	public RoomCategory(final ResultSet categoryData) throws SQLException
	{
		this._id = categoryData.getInt("id");
		this._minRank = categoryData.getInt("min_rank");
		this._caption = categoryData.getString("caption");
		this._canTrade = (categoryData.getInt("can_trade") == 1);
		this._categoryType = CategoryType.NORMAL; // TODO: Read this from database.
	}

	public final int getId()
	{
		return this._id;
	}

	public final int getMinRank()
	{
		return this._minRank;
	}

	public final String getCaption()
	{
		return this._caption;
	}

	public final boolean getCanTrade()
	{
		return this._canTrade;
	}

	public final CategoryType getCategoryType()
	{
		return this._categoryType;
	}

}
