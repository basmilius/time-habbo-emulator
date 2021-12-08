package com.basmilius.time.habbohotel.reception;

import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceptionSpotlightItem implements ISerialize
{

	private int _id;
	private String _title;
	private String _text;
	private String _btnText;
	private int _btnType;
	private String _btnLink;
	private String _image;

	public ReceptionSpotlightItem(ResultSet _result) throws SQLException
	{
		this._id = _result.getInt("id");
		this._title = _result.getString("title");
		this._text = _result.getString("text");
		this._btnText = _result.getString("button_text");
		this._btnType = ((_result.getString("button_type").equals("client")) ? 1 : 0);
		this._btnLink = _result.getString("button_link");
		this._image = _result.getString("image");
	}

	public int getId()
	{
		return this._id;
	}

	public String getTitle()
	{
		return this._title;
	}

	public String getText()
	{
		return this._text;
	}

	public String getBtnText()
	{
		return this._btnText;
	}

	public int getBtnType()
	{
		return this._btnType;
	}

	public String getBtnLink()
	{
		return this._btnLink;
	}

	public String getImage()
	{
		return this._image;
	}

	@Override
	public void serialize(ServerMessage response)
	{
		response.appendInt(this._id);
		response.appendString(this._title);
		response.appendString(this._text);
		response.appendString(this._btnText);
		response.appendInt(this._btnType);
		response.appendString(this._btnLink);
		response.appendString(this._image);
	}

}
